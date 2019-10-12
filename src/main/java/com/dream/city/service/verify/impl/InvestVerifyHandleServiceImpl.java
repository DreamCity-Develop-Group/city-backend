package com.dream.city.service.verify.impl;

import com.dream.city.base.Result;
import com.dream.city.base.model.entity.InvestOrder;
import com.dream.city.base.model.entity.PlayerTrade;
import com.dream.city.base.model.entity.TradeVerify;
import com.dream.city.base.model.enu.*;
import com.dream.city.base.model.req.VerifyReq;
import com.dream.city.base.model.resp.InvestOrderResp;
import com.dream.city.service.invest.OrderService;
import com.dream.city.service.trade.PlayerTradeService;
import com.dream.city.service.verify.InvestVerifyHandleService;
import com.dream.city.service.verify.TradeVerifyService;
import com.dream.city.service.verify.VerifyCommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class InvestVerifyHandleServiceImpl implements InvestVerifyHandleService {


    @Autowired
    TradeVerifyService verifyService;
    @Autowired
    OrderService orderService;
    @Autowired
    PlayerTradeService tradeService;
    @Autowired
    VerifyCommonService verifyCommonService;


    @Override
    @Transactional
    public Result subscribeOrderVerify(VerifyReq verifyReq) {
        boolean success = Boolean.FALSE;
        String descr = "预约审核失败";
        //获取审核订单
        InvestOrderResp order = orderService.getInvestOrderById(verifyReq.getOrderId());
        //审核将状态改成投资
        int i = 0;
        if (order == null){
            descr = "没找到预约记录";
        }else {
            //获取订单交易金额
            PlayerTrade tradeReq = new PlayerTrade();
            tradeReq.setTradeOrderId(verifyReq.getOrderId());
            tradeReq.setTradePlayerId(order.getPayerId());
            PlayerTrade playerTrade = tradeService.getPlayerTrade(tradeReq);

            //必须是预约中的
            if (InvestStatus.SUBSCRIBED.getDesc().equalsIgnoreCase(order.getOrderState()) && playerTrade != null){
                //修改订单状态
                if (VerifyStatus.PASS.getCode().equalsIgnoreCase(verifyReq.getVerifyStatus())){
                    //审核通过 预约中->经营中
                    i = this.updateOrderState(verifyReq.getOrderId(), InvestStatus.MANAGEMENT.name());
                }else {
                    //审核不通过 预约中->预约审核不通过
                    i = this.updateOrderState(verifyReq.getOrderId(), InvestStatus.SUBSCRIBE_VERIFY_FAIL.name());
                }
                if (i > 0){
                    success = Boolean.TRUE;
                    descr = "预约审核成功，审核状态为："+verifyReq.getVerifyStatus();

                    //生成审核记录
                    TradeVerify verify = this.insertTradeVerify(verifyReq);

                    if (VerifyStatus.PASS.getCode().equalsIgnoreCase(verifyReq.getVerifyStatus())){
                        //扣除冻结投资金额
                        //扣除冻结usdt投资金额
                        String amountType = "usdtfreeze";
                        i = verifyCommonService.playerSubtractAmount(order.getPayerId(),playerTrade.getTradeAmount(),amountType);
                        //扣除冻结mt投资税
                        amountType = "mtfreeze";
                        if (i > 0){
                            //扣个人所得税
                            i = verifyCommonService.playerSubtractAmount(order.getPayerId(),playerTrade.getPersonalTax(),amountType);
                        }
                        if (i > 0){
                            //扣企业所得税
                            i = verifyCommonService.playerSubtractAmount(order.getPayerId(),playerTrade.getEnterpriseTax(),amountType);
                        }
                        if (i > 0){
                            //生成交易记录
                            playerTrade = createPlayerTrade(order.getPayerId(),verifyReq.getOrderId(),playerTrade);

                            //生成交易流水
                            //扣除冻结usdt投资金额流水
                            verifyCommonService.createTradeDetail(order.getPayerId(),verifyReq.getOrderId(), playerTrade.getTradeId(),verify.getVerifyId(),
                                    playerTrade.getTradeAmount(),TradeDetailType.USDT_INVEST_VERIFY.getCode(),TradeDetailType.USDT_INVEST_VERIFY.getDesc());
                            //扣个人所得税流水
                            verifyCommonService.createTradeDetail(order.getPayerId(),verifyReq.getOrderId(), playerTrade.getTradeId(), verify.getVerifyId(),
                                    playerTrade.getPersonalTax(),TradeDetailType.MT_INVEST_PERSONAL_TAX.getCode(),TradeDetailType.MT_INVEST_PERSONAL_TAX.getDesc());
                            //扣企业所得税流水
                            verifyCommonService.createTradeDetail(order.getPayerId(),verifyReq.getOrderId(), playerTrade.getTradeId(), verify.getVerifyId(),
                                    playerTrade.getEnterpriseTax(),TradeDetailType.MT_INVEST_ENTERPRISE_TAX.getCode(),TradeDetailType.MT_INVEST_ENTERPRISE_TAX.getDesc());
                        }
                    }
                }

            }else {
                descr = "预约投资状态不对，当前状态是：" + order.getOrderState();
            }
        }
        return new Result(success,descr,i);
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
                i = this.updateOrderState(verifyReq.getOrderId(), InvestStatus.MANAGEMENT.name());
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


    private PlayerTrade createPlayerTrade(String payerId,Integer orderId,PlayerTrade playerTrade){
        PlayerTrade tradeReq = new PlayerTrade();
        tradeReq.setTradePlayerId(payerId);
        tradeReq.setTradeOrderId(orderId);
        tradeReq.setTradeType(TradeType.INVEST.getCode());
        tradeReq.setInOutStatus(AmountDynType.OUT.getCode());
        tradeReq.setEnterpriseTax(playerTrade.getEnterpriseTax());
        tradeReq.setPersonalTax(playerTrade.getPersonalTax());
        tradeReq.setTradeAccId(playerTrade.getTradeAccId());
        tradeReq.setTradeAmount(playerTrade.getTradeAmount());
        tradeReq.setTradeDesc("扣除冻结投资金额");
        return tradeService.insertPlayerTrade(tradeReq);
    }

    private TradeVerify insertTradeVerify(VerifyReq verifyReq){
        TradeVerify record = new TradeVerify();
        record.setVerifyOrderId(verifyReq.getOrderId());
        record.setVerifyStatus(verifyReq.getVerifyStatus());
        record.setVerifyDesc(verifyReq.getVerifyDesc());
        record.setVerifyEmpId(verifyReq.getEmpId());
        return verifyService.insertTradeVerify(record);
    }


    private int updateOrderState(Integer orderId,String orderState){
        InvestOrder orderReq = new InvestOrder();
        orderReq.setOrderId(orderId);
        orderReq.setOrderState(orderState);
        return orderService.updateOrderStateById(orderReq);
    }


}
