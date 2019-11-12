package com.dream.city.service.verify.impl;

import com.alibaba.fastjson.JSONObject;
import com.dream.city.base.Result;
import com.dream.city.base.exception.BusinessException;
import com.dream.city.base.model.entity.*;
import com.dream.city.base.model.enu.*;
import com.dream.city.base.model.req.VerifyReq;
import com.dream.city.base.model.resp.InvestOrderResp;
import com.dream.city.service.account.AccountService;
import com.dream.city.service.invest.OrderService;
import com.dream.city.service.trade.PlayerTradeService;
import com.dream.city.service.verify.InvestVerifyHandleService;
import com.dream.city.service.verify.TradeVerifyService;
import com.dream.city.service.verify.VerifyCommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;


/**
 * @author
 */
@Service
public class InvestVerifyHandleServiceImpl implements InvestVerifyHandleService {


    @Autowired
    TradeVerifyService verifyService;
    @Autowired
    OrderService orderService;
    @Autowired
    PlayerTradeService tradeService;
    @Autowired
    AccountService accountService;
    @Autowired
    VerifyCommonService verifyCommonService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<JSONObject> subscribeOrderVerify(VerifyReq verifyReq) throws BusinessException {
        boolean success = Boolean.FALSE;
        String descr = "预约审核失败";
        JSONObject json = new JSONObject();
        //获取审核订单
        InvestOrderResp order = orderService.getInvestOrderById(verifyReq.getOrderId());
        //审核将状态改成投资
        Result<Integer> result = null;
        if (order == null){
            descr = "没找到预约记录";
        }else {
            //获取订单交易金额
            PlayerTrade tradeReq = new PlayerTrade();
            tradeReq.setTradeId(verifyReq.getTradeId());
            tradeReq.setTradeOrderId(verifyReq.getOrderId());
            tradeReq.setTradePlayerId(order.getPlayerId());
            tradeReq.setTradeStatus(TradeStatus.FREEZE.getCode());
            PlayerTrade playerTrade = tradeService.getPlayerTrade(tradeReq);

            //必须是预约中的
            if (InvestStatus.SUBSCRIBED.getDesc().equalsIgnoreCase(order.getOrderState()) && playerTrade != null){
                //修改订单状态
                result = this.updateOrderStatus(verifyReq);
                success = result.getSuccess();
                descr = result.getMsg();

                if (result.getSuccess() && result.getData() > 0){
                    //生成审核记录
                    TradeVerify verify = this.insertTradeVerify(verifyReq);

                    if (VerifyStatus.PASS.getCode().equalsIgnoreCase(verifyReq.getVerifyStatus())){
                        //审核通过扣除冻结金额
                        //玩家账户扣除冻结投资金额
                        Result<JSONObject> playerAcountSubtractAmountResult = this.playerAcountSubtractAmount(verifyReq, playerTrade, order, verify);
                        //平台账户进账
                        if (playerAcountSubtractAmountResult.getSuccess()){
                            json.putAll(playerAcountSubtractAmountResult.getData());
                            this.platformAcountAddAmount(verifyReq, playerTrade, order.getOrderId(), verify);
                        }
                    }else if (VerifyStatus.NOTPASS.getCode().equalsIgnoreCase(verifyReq.getVerifyStatus())){
                        //审核不通过解冻金额
                        result = this.unfreezePlayerAccount(order.getPlayerId(),  playerTrade.getTradeId(),  playerTrade.getTradeAmount(),
                                order.getOrderId(), playerTrade.getPersonalTax(), playerTrade.getEnterpriseTax(), playerTrade.getQuotaTax(), verify.getVerifyId());
                        success = result.getSuccess();
                        descr = result.getMsg();
                    }
                    if (success) {
                        descr = "预约审核成功";
                    }
                }
            }else {
                descr = "预约投资状态不对，当前状态是：" + order.getOrderState();
            }
        }
        return new Result<>(success,descr,json);
    }


    /**
     * 审核不通过解冻金额
     * @param playerId
     * @param verifyId
     * @return
     */
    private Result<Integer> unfreezePlayerAccount(String playerId, Integer tradeId, BigDecimal tradeAmount,Integer orderId,
                                         BigDecimal personalTax,BigDecimal enterpriseTax,BigDecimal quotaTax,Integer verifyId){
        boolean success = Boolean.FALSE;
        String msg = "";
        //解冻usdt
        int updatePlayerAccount = verifyCommonService.unfreezePlayerAccount(playerId, tradeAmount,
                "usdtUnfreeze", "预约投资审核不通过解冻USDT");
        //解冻mt税金
        BigDecimal tax = personalTax.add(enterpriseTax).add(quotaTax);
        if (updatePlayerAccount > 0){
            updatePlayerAccount = verifyCommonService.unfreezePlayerAccount(playerId, tax,
                    "mtUnfreeze",  "预约投资审核不通过解冻MT税金");
        }else {
            msg = "预约投资审核不通过解冻USDT失败";
        }

        //更新解冻交易记录
        PlayerTrade createPlayerTrade = null;
        if (updatePlayerAccount > 0){
            createPlayerTrade = verifyCommonService.updatePlayerTradeStatus(tradeId,
                    TradeType.INVEST.getCode(),TradeStatus.UNFREEZE.getCode(),AmountDynType.IN.getCode(),
                    "预约投资审核不通过解冻USDT");
        }else {
            msg = "预约投资审核不通过解冻MT税金失败";
        }

        //生成解冻交易流水
        TradeDetail createPlayerTradeDetai = null;
        if (createPlayerTrade != null){
            createPlayerTradeDetai = verifyCommonService.createTradeDetail(playerId,  orderId,
                    tradeId,  verifyId, tradeAmount,
                    TradeDetailType.USDT_INVEST_UNFREEZE.getCode(), "预约投资审核不通过解冻USDT");
        }else {
            msg = "预约投资审核不通过解冻USDT失败";
        }
        if (createPlayerTradeDetai != null && personalTax.compareTo(BigDecimal.ZERO) > 0){
            createPlayerTradeDetai = verifyCommonService.createTradeDetail(playerId,  orderId,tradeId,  verifyId, personalTax,
                    TradeDetailType.MT_INVEST_PERSONAL_TAX_UNFREEZE.getCode(), "预约投资审核不通过解冻个人所得税");
        }else {
            msg = "预约投资审核不通过解冻USDT失败";
        }
        if (createPlayerTradeDetai != null && enterpriseTax.compareTo(BigDecimal.ZERO) > 0){
            createPlayerTradeDetai = verifyCommonService.createTradeDetail(playerId,  orderId, tradeId,  verifyId, enterpriseTax,
                    TradeDetailType.MT_INVEST_ENTERPRISE_TAX_UNFREEZE.getCode(), "预约投资审核不通过解冻企业所得税");
        }else {
            msg = "预约投资审核不通过解冻个人所得税失败";
        }
        if (createPlayerTradeDetai != null && quotaTax.compareTo(BigDecimal.ZERO) > 0){
            createPlayerTradeDetai = verifyCommonService.createTradeDetail(playerId,  orderId, tradeId,  verifyId, quotaTax,
                    TradeDetailType.MT_INVEST_QUOTA_TAX_UNFREEZE.getCode(), "预约投资审核不通过解冻定额税");
        }else {
            msg = "预约投资审核不通过解冻企业所得税失败";
        }

        if (createPlayerTradeDetai == null){
            msg = "预约投资审核不通过解冻定额税失败";
        }else {
            success = Boolean.TRUE;
            msg = "审核成功！";
        }
        return new Result<>(success,msg);
    }


    /**
     * 平台账户进账操作
     * @param verifyReq
     * @param playerTrade
     * @param verify
     */
    private void platformAcountAddAmount(VerifyReq verifyReq,PlayerTrade playerTrade,Integer orderId,
                                         TradeVerify verify) throws BusinessException {
        List<PlayerAccount> platformAccounts = accountService.getPlatformAccounts(null);
        if (CollectionUtils.isEmpty(platformAccounts)){
            throw new BusinessException("找不到平台账户");
        }else {
            PlayerAccount platformAccount = platformAccounts.get(0);
            //扣除玩家冻结usdt投资金额加到平台账户，提取时才扣除手续费
            platformAccount = verifyCommonService.platformAddAmount(playerTrade.getTradeAmount(),"usdt");
            //生成平台账户交易记录
            if (platformAccount != null){
                playerTrade = verifyCommonService.createPlayerTrade(platformAccount.getAccPlayerId(),orderId,playerTrade,
                        TradeType.RECEIVABLES.getCode(),TradeStatus.IN.getCode(),AmountDynType.IN.getCode(),
                        "tradeAmount","将扣除玩家冻结usdt投资金额加到平台账户");
            }
            //生成平台账户交易流水
            if (playerTrade != null){
                verifyCommonService.createTradeDetail(platformAccount.getAccPlayerId(),
                        verifyReq.getOrderId(), playerTrade.getTradeId(), verify.getVerifyId(),
                        playerTrade.getTradeAmount(), TradeDetailType.RECEIVABLES_INVEST_USDT.getCode(),
                        "将扣除玩家冻结usdt投资金额加到平台账户");
            }
        }
    }

    /**
     * 玩家账户扣除冻结投资金额
     */
    private Result<JSONObject> playerAcountSubtractAmount(VerifyReq verifyReq,PlayerTrade playerTrade,InvestOrderResp order,TradeVerify verify){
        boolean success = Boolean.FALSE;
        String msg = "扣除冻结投资金额失败";
        JSONObject json = new JSONObject();

        //扣除冻结usdt投资金额
        Result<JSONObject> result = verifyCommonService.playerSubtractAmount(order.getPlayerId(),playerTrade.getTradeAmount(),"usdtfreeze");
        json.put("tradeAmount",playerTrade.getTradeAmount());
        json.put("personalTax",playerTrade.getPersonalTax());
        json.put("enterpriseTax",playerTrade.getPersonalTax());
        json.put("quotaTax",playerTrade.getPersonalTax());

        //扣除冻结mt投资税
        if (result.getSuccess() && playerTrade.getPersonalTax().compareTo(BigDecimal.ZERO) > 0){
            json.putAll(result.getData());
            json.put("personalTax",playerTrade.getPersonalTax());
            //扣个人所得税
            result = verifyCommonService.playerSubtractAmount(order.getPlayerId(),playerTrade.getPersonalTax(),"mtfreeze");
        }
        if (result.getSuccess() && playerTrade.getEnterpriseTax().compareTo(BigDecimal.ZERO) > 0){
            json.put("enterpriseTax",playerTrade.getEnterpriseTax());
            //扣企业所得税
            result = verifyCommonService.playerSubtractAmount(order.getPlayerId(),playerTrade.getEnterpriseTax(),"mtfreeze");
        }
        if (result.getSuccess() && playerTrade.getQuotaTax().compareTo(BigDecimal.ZERO) > 0){
            json.put("quotaTax",playerTrade.getQuotaTax());
            //扣定额税
            result = verifyCommonService.playerSubtractAmount(order.getPlayerId(),playerTrade.getQuotaTax(),"mtfreeze");
        }

        //更新交易记录
        if (result.getSuccess()){
            playerTrade = verifyCommonService.updatePlayerTradeStatus(playerTrade.getTradeId(),
                    TradeType.INVEST.getCode(),TradeStatus.OUT.getCode(),AmountDynType.OUT.getCode(),"已扣除冻结投资金额");
        }

        //生成交易流水
        TradeDetail tradeDetail = null;
        if (playerTrade != null){
            //扣除冻结usdt投资金额流水
            tradeDetail = verifyCommonService.createTradeDetail(order.getPlayerId(), verifyReq.getOrderId(), playerTrade.getTradeId(), verify.getVerifyId(),
                    playerTrade.getTradeAmount(), TradeDetailType.USDT_INVEST_VERIFY.getCode(), TradeDetailType.USDT_INVEST_VERIFY.getDesc());
        }
        if (tradeDetail != null && playerTrade.getPersonalTax().compareTo(BigDecimal.ZERO) > 0){
            //扣个人所得税流水
            tradeDetail = verifyCommonService.createTradeDetail(order.getPlayerId(),verifyReq.getOrderId(), playerTrade.getTradeId(), verify.getVerifyId(),
                    playerTrade.getPersonalTax(),TradeDetailType.MT_INVEST_PERSONAL_TAX.getCode(),TradeDetailType.MT_INVEST_PERSONAL_TAX.getDesc());
        }
        if (tradeDetail != null && playerTrade.getEnterpriseTax().compareTo(BigDecimal.ZERO) > 0){
            //扣企业所得税流水
            tradeDetail = verifyCommonService.createTradeDetail(order.getPlayerId(),verifyReq.getOrderId(), playerTrade.getTradeId(), verify.getVerifyId(),
                    playerTrade.getEnterpriseTax(),TradeDetailType.MT_INVEST_ENTERPRISE_TAX.getCode(),TradeDetailType.MT_INVEST_ENTERPRISE_TAX.getDesc());
        }
        if (tradeDetail != null && playerTrade.getQuotaTax().compareTo(BigDecimal.ZERO) > 0){
            //扣定额税流水
            tradeDetail = verifyCommonService.createTradeDetail(order.getPlayerId(),verifyReq.getOrderId(), playerTrade.getTradeId(), verify.getVerifyId(),
                    playerTrade.getQuotaTax(),TradeDetailType.MT_INVEST_QUOTA_TAX.getCode(),TradeDetailType.MT_INVEST_QUOTA_TAX.getDesc());
        }


        if (tradeDetail != null){
            success = Boolean.TRUE;
            msg = "扣除冻结投资金额成功";
        }
        return new Result<>(success,msg,json);
    }

    /**
     * 修改订单状态
     * @return
     */
    private Result<Integer> updateOrderStatus(VerifyReq verifyReq){
        String msg = "";
        boolean success = Boolean.TRUE;
        int i = 0;
        if (VerifyStatus.PASS.getCode().equalsIgnoreCase(verifyReq.getVerifyStatus())){
            //审核通过 预约中->经营中
            i = this.updateOrderState(verifyReq.getOrderId(), InvestStatus.MANAGEMENT.getStatus());
            if (i < 1){
                msg = "审核通过修改订单状态失败";
                success = Boolean.FALSE;
            }
        }else {
            //审核不通过 预约中->预约审核不通过
            i = this.updateOrderState(verifyReq.getOrderId(), InvestStatus.SUBSCRIBE_VERIFY_FAIL.getStatus());
            if (i < 1){
                msg = "审核不通过修改订单状态失败";
                success = Boolean.FALSE;
            }
        }
        return new Result<>(success,msg,i);
    }


    private TradeVerify insertTradeVerify(VerifyReq verifyReq){
        TradeVerify record = new TradeVerify();
        record.setVerifyOrderId(verifyReq.getOrderId());
        record.setVerifyStatus(verifyReq.getVerifyStatus());
        record.setVerifyDesc(verifyReq.getVerifyDesc());
        record.setVerifyEmpId(verifyReq.getEmpId());
        return verifyService.insertTradeVerify(record);
    }


    private int updateOrderState(Integer orderId,Integer orderState){
        InvestOrder orderReq = new InvestOrder();
        orderReq.setOrderId(orderId);
        orderReq.setOrderState(orderState);
        return orderService.updateOrderStateById(orderReq);
    }




    @Override
    public Result investOrderVerify(VerifyReq verifyReq) {
        boolean success = Boolean.FALSE;
        String descr = "投资审核失败";
        InvestOrderResp order = orderService.getInvestOrderById(verifyReq.getOrderId());
        int i = 0;
        if (order == null){
            descr = "没找到预约记录";
        }else {
            if (InvestStatus.INVESTED.name().equalsIgnoreCase(order.getOrderState())){
                //审核成功，将状态改成经营中
                i = this.updateOrderState(verifyReq.getOrderId(), InvestStatus.MANAGEMENT.getStatus());
                if (i > 0){
                    success = Boolean.TRUE;
                    descr = "预约审核成功";
                    //生成审核记录
                    this.insertTradeVerify(verifyReq);
                }
            }else {
                descr = "预约投资状态不对，当前状态是：" + order.getOrderState();
            }
        }
        if (i > 0){
            success = Boolean.TRUE;
            descr = "投资审核成功";
            //生成审核记录
            this.insertTradeVerify(verifyReq);

            String tradeType = VerifyType.INVEST.name();
            //玩家账户扣除usdt投资金额


            //玩家账户扣除usdt投资金额流水



            //平台usdt账户增加投资金额


            //平台usdt账户增加投资金额流水

        }
        return new Result(success,descr,i);
    }

}
