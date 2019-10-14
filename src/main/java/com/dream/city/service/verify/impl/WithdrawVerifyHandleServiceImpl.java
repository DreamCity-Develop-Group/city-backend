package com.dream.city.service.verify.impl;

import com.dream.city.base.exception.BusinessException;
import com.dream.city.base.model.entity.*;
import com.dream.city.base.model.enu.*;
import com.dream.city.service.account.AccountService;
import com.dream.city.base.Result;
import com.dream.city.base.model.req.VerifyReq;
import com.dream.city.service.trade.EarningService;
import com.dream.city.service.trade.PlayerTradeService;
import com.dream.city.service.verify.TradeVerifyService;
import com.dream.city.service.verify.VerifyCommonService;
import com.dream.city.service.verify.WithdrawVerifyHandleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author
 */
@Service
public class WithdrawVerifyHandleServiceImpl implements WithdrawVerifyHandleService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    AccountService accountService;
    @Autowired
    PlayerTradeService tradeService;
    @Autowired
    TradeVerifyService verifyService;
    @Autowired
    EarningService earningService;
    @Autowired
    VerifyCommonService verifyCommonService;



    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Boolean> withdrawVerify(VerifyReq record) {
        boolean success = Boolean.FALSE;
        String msg = "";
        if (record.getTradeId() == null || (record.getTradeId() != null &&record.getTradeId() < 0)){
            msg = "交易id不能为空";
            logger.error("审核失败，{}",msg);
            return new Result<>(success,msg);
        }
        //获取交易记录
        PlayerTrade tradeReq = new PlayerTrade();
        tradeReq.setTradeId(record.getTradeId());
        PlayerTrade playerTrade = tradeService.getPlayerTrade(tradeReq);

        //生成审核记录
        TradeVerify verify = verifyCommonService.createVerify(record);
        Result<Boolean> result = null;
        if (record.getVerifyStatus().equalsIgnoreCase(VerifyStatus.PASS.name())){
            //审核通过
            //玩家账户扣除金额
            result = this.playerAcountSubtractAmount(playerTrade,verify.getVerifyId());

            //扣除税金加到平台账户
            if (result.getSuccess()){
                this.platformAcountAddAmount(record, playerTrade,verify);
            }
        }else if (record.getVerifyStatus().equalsIgnoreCase(VerifyStatus.NOTPASS.name())){
            //审核不通过,返回冻结金额
            result = this.unfreezePlayerAccount(playerTrade,verify.getVerifyId());
        }
        return result;
    }

    /**
     * 平台账户进账操作
     * 扣除玩家冻结5MT税金加到平台账户
     * @param verifyReq
     * @param playerTrade
     * @param verify
     */
    private void platformAcountAddAmount(VerifyReq verifyReq,PlayerTrade playerTrade,TradeVerify verify) throws BusinessException {
        List<PlayerAccount> platformAccounts = accountService.getPlatformAccounts(null);
        if (CollectionUtils.isEmpty(platformAccounts)){
            throw new BusinessException("找不到平台账户");
        }else {
            PlayerAccount platformAccount = platformAccounts.get(0);
            //扣除玩家冻结5MT税金加到平台账户
            platformAccount = verifyCommonService.platformAddAmount(playerTrade.getPersonalTax(),"mt");
            //生成平台账户交易记录
            if (platformAccount != null){
                playerTrade = verifyCommonService.createPlayerTrade(platformAccount.getAccPlayerId(),null,playerTrade,
                        TradeType.RECEIVABLES.getCode(),TradeStatus.IN.getCode(),AmountDynType.IN.getCode(),
                        "personalTax","将扣除玩家冻结税金加到平台账户");
            }
            //生成平台账户交易流水
            if (playerTrade != null){
                verifyCommonService.createTradeDetail(platformAccount.getAccPlayerId(),
                        verifyReq.getOrderId(), playerTrade.getTradeId(), verify.getVerifyId(),
                        playerTrade.getPersonalTax(), TradeDetailType.RECEIVABLES_INVEST_TAX.getCode(),
                        "将扣除玩家冻结税金加到平台账户");
            }
        }
    }

    private Result<Boolean> unfreezePlayerAccount(PlayerTrade playerTrade,Integer verifyId){
        boolean success = Boolean.FALSE;
        String msg = "";
        //解冻usdt
        int updatePlayerAccount = verifyCommonService.unfreezePlayerAccount(playerTrade.getTradePlayerId(), playerTrade.getTradeAmount(),
                "usdtUnfreeze", "审核不通过返回冻结USDT");
        //解冻5mt税金
        if (updatePlayerAccount > 0){
            updatePlayerAccount = verifyCommonService.unfreezePlayerAccount(playerTrade.getTradePlayerId(), playerTrade.getPersonalTax(),
                    "mtUnfreeze",  "审核不通过返回冻结5MT税金");
        }else {
            msg = "审核不通过返回冻结USDT金额失败";
        }

        //更新解冻交易记录
        PlayerTrade createPlayerTrade = null;
        if (updatePlayerAccount > 0){
            createPlayerTrade = verifyCommonService.updatePlayerTradeStatus(playerTrade.getTradeId(),
                    TradeType.WITHDRAW.getCode(),TradeStatus.UNFREEZE.getCode(),AmountDynType.IN.getCode(),
                    "审核不通过解冻扣除金额");
        }else {
            msg = "审核不通过返回冻结金额5MT税金失败";
        }
        //生成解冻交易流水
        TradeDetail createPlayerTradeDetai = null;
        if (createPlayerTrade != null){
            createPlayerTradeDetai = verifyCommonService.createTradeDetail(playerTrade.getTradePlayerId(),  null,
                    playerTrade.getTradeId(),  verifyId, playerTrade.getTradeAmount(),
                    TradeDetailType.WITHDRAW_UNFREEZE_USDT.getCode(), "审核不通过解冻扣除USDT");
        }else {
            msg = "审核不通过解冻扣除USDT失败";
        }
        if (createPlayerTradeDetai != null){
            createPlayerTradeDetai = verifyCommonService.createTradeDetail(playerTrade.getTradePlayerId(),  null,
                    playerTrade.getTradeId(),  verifyId, playerTrade.getTradeAmount(),
                    TradeDetailType.WITHDRAW_UNFREEZE_MT.getCode(), "审核不通过解冻扣除MT税金");
        }else {
            msg = "审核不通生成解冻交易流水失败";
        }
        if (createPlayerTradeDetai == null){
            msg = "审核不通过解冻扣除MT税金失败";
        }else {
            success = Boolean.TRUE;
            msg = "审核成功！";
        }
        return new Result<>(success,msg);
    }

    /**
     * 玩家账户扣除金额 扣冻结Usdt金额
     * @param playerTrade
     */
    private Result<Boolean> playerAcountSubtractAmount(PlayerTrade playerTrade,Integer verifyId){
        boolean success = Boolean.FALSE;
        String msg = "";
        int updatePlayerAccount = verifyCommonService.playerSubtractAmount(playerTrade.getTradePlayerId(),
                playerTrade.getTradeAmount(),"usdtfreeze");
        //玩家账户扣除税金 扣冻结税金5mt
        //todo 5mt改为从规则中取
        if (updatePlayerAccount > 0){
            updatePlayerAccount = verifyCommonService.playerSubtractAmount(playerTrade.getTradePlayerId(),
                    playerTrade.getPersonalTax(), "mtfreeze");
        }else {
            msg = "玩家账户扣除金额失败";
        }

        //更新玩家交易记录
        PlayerTrade createPlayerTrade = null;
        if (updatePlayerAccount > 0){
            createPlayerTrade = verifyCommonService.updatePlayerTradeStatus(playerTrade.getTradeId(),
                    TradeType.WITHDRAW.getCode(),TradeStatus.OUT.getCode(),AmountDynType.OUT.getCode(),
                    "审核通过扣除USDT冻结金额");
        }else {
            msg = "玩家账户扣除税金失败";
        }

        //玩家账户扣除USDT流水
        TradeDetail createPlayerTradeDetai = null;
        if (createPlayerTrade != null){
            createPlayerTradeDetai = verifyCommonService.createTradeDetail(playerTrade.getTradePlayerId(),  null,
                    playerTrade.getTradeId(),  verifyId, playerTrade.getTradeAmount(),
                    TradeDetailType.WITHDRAW_VERIFY.getCode(), "审核通过扣除USDT冻结金额");
        }else {
            msg = "生成玩家账户扣除金额记录失败";
        }
        //玩家账户扣除税金流水
        if (createPlayerTradeDetai != null){
            createPlayerTradeDetai = verifyCommonService.createTradeDetail(playerTrade.getTradePlayerId(),  null,
                    playerTrade.getTradeId(),  verifyId, playerTrade.getPersonalTax(),
                    TradeDetailType.WITHDRAW_VERIFY.getCode(), "审核通过扣除冻结税金");
        }else {
            msg = "生成玩家账户扣除流水失败";
        }
        if (createPlayerTradeDetai == null){
            msg = "审核通过扣除冻结税金失败";
        }else {
            success = Boolean.TRUE;
            msg = "审核成功！";
        }
        return new Result<>(success,msg);
    }




    @Override
    public Result withdrawSubscribe(VerifyReq record) {
        return null;
    }


}
