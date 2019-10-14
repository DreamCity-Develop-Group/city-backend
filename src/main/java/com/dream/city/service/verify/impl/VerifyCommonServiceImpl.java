package com.dream.city.service.verify.impl;

import com.alibaba.fastjson.JSONObject;
import com.dream.city.base.Result;
import com.dream.city.base.model.entity.*;
import com.dream.city.service.account.AccountService;
import com.dream.city.base.model.req.VerifyReq;
import com.dream.city.service.trade.EarningService;
import com.dream.city.service.trade.PlayerTradeService;
import com.dream.city.service.trade.TradeDetailService;
import com.dream.city.service.verify.TradeVerifyService;
import com.dream.city.service.verify.VerifyCommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author
 */
@Service
public class VerifyCommonServiceImpl implements VerifyCommonService {

    @Autowired
    AccountService accountService;
    @Autowired
    PlayerTradeService tradeService;
    @Autowired
    EarningService earningService;
    @Autowired
    TradeVerifyService verifyService;
    @Autowired
    TradeDetailService detailService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<JSONObject> playerSubtractAmount(String accPlayerId, BigDecimal amount, String amountType) {
        boolean success = Boolean.FALSE;
        String msg = "扣减金额失败";
        PlayerAccount getPlayerAccountReq = new PlayerAccount();
        getPlayerAccountReq.setAccPlayerId(accPlayerId);
        PlayerAccount playerAccount = accountService.getPlayerAccount(getPlayerAccountReq);
        //玩家账户扣除金额 扣冻结Usdt金额
        PlayerAccount updateAccountReq = new PlayerAccount();
        updateAccountReq.setAccPlayerId(playerAccount.getAccPlayerId());
        if ("usdtfreeze".equalsIgnoreCase(amountType)) {
            updateAccountReq.setAccUsdtFreeze(playerAccount.getAccUsdtFreeze().subtract(amount));
        }
        if ("usdt".equalsIgnoreCase(amountType)) {
            updateAccountReq.setAccUsdt(playerAccount.getAccUsdt().subtract(amount));
            updateAccountReq.setAccUsdtAvailable(playerAccount.getAccUsdtAvailable().subtract(amount));
        }
        if ("mtfreeze".equalsIgnoreCase(amountType)) {
            updateAccountReq.setAccMtFreeze(playerAccount.getAccMtFreeze().subtract(amount));
        }
        if ("mt".equalsIgnoreCase(amountType)) {
            updateAccountReq.setAccMt(playerAccount.getAccMt().subtract(amount));
            updateAccountReq.setAccMtAvailable(playerAccount.getAccMtAvailable().subtract(amount));
        }
        int i = accountService.updatePlayerAccount(updateAccountReq);
        JSONObject json = new JSONObject();
        if (i > 0){
            success = Boolean.TRUE;
            msg = "扣减金额成功";
            json.put("accAddr",playerAccount.getAccAddr());
            json.put("playerId",playerAccount.getAccPlayerId());
        }
        return new Result<>(success,msg,json);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public PlayerAccount platformAddAmount(BigDecimal amount,String amountType) {
        //获取平台账户
        List<PlayerAccount>  platformAccountList = accountService.getPlatformAccounts(null);
        PlayerAccount platformAccount = null;
        if (!CollectionUtils.isEmpty(platformAccountList)){
            platformAccount = platformAccountList.get(0);
        }
        int updatePlayerAccount = 0;
        if (platformAccount != null){
            //把玩家账户扣除usdt 加到平台usdt账户
            if ("usdt".equalsIgnoreCase(amountType)) {
                platformAccount.setAccUsdt(platformAccount.getAccUsdt().add(amount));
                platformAccount.setAccUsdtAvailable(platformAccount.getAccUsdtAvailable().add(amount));
            }
            if ("mt".equalsIgnoreCase(amountType)) {
                platformAccount.setAccMt(platformAccount.getAccMt().add(amount));
                platformAccount.setAccMtAvailable(platformAccount.getAccMtAvailable().add(amount));
            }
            //更新平台usdt账户
            updatePlayerAccount = accountService.updatePlayerAccount(platformAccount);
        }
        if (updatePlayerAccount < 1){
            return null;
        }
        return platformAccount;
    }


    /**
     * 生成交易
     * @param payerId
     * @param orderId
     * @param playerTrade
     * @return
     */
    @Override
    public PlayerTrade createPlayerTrade(String payerId,Integer orderId,PlayerTrade playerTrade,String tradeType,
                                          String tradeStatus,String inOutStatus,String amountType,String descr){
        PlayerTrade tradeReq = new PlayerTrade();
        tradeReq.setTradePlayerId(payerId);
        tradeReq.setTradeOrderId(orderId);
        tradeReq.setTradeType(tradeType);
        tradeReq.setTradeStatus(tradeStatus);
        tradeReq.setInOutStatus(inOutStatus);
        tradeReq.setTradeAccId(playerTrade.getTradeAccId());
        if ("personalTax".equalsIgnoreCase(amountType)) {
            tradeReq.setPersonalTax(playerTrade.getPersonalTax());
        }else if ("enterpriseTax".equalsIgnoreCase(amountType)) {
            tradeReq.setEnterpriseTax(playerTrade.getEnterpriseTax());
        }else if ("tradeAmount".equalsIgnoreCase(amountType)) {
            tradeReq.setTradeAmount(playerTrade.getTradeAmount());
        }else if ("tax".equalsIgnoreCase(amountType)) {
            tradeReq.setPersonalTax(playerTrade.getPersonalTax());
            tradeReq.setEnterpriseTax(playerTrade.getEnterpriseTax());
        }else {
            tradeReq.setPersonalTax(playerTrade.getPersonalTax());
            tradeReq.setEnterpriseTax(playerTrade.getEnterpriseTax());
            tradeReq.setTradeAmount(playerTrade.getTradeAmount());
        }
        tradeReq.setTradeDesc(descr);
        return tradeService.insertPlayerTrade(tradeReq);
    }

    @Override
    public PlayerTrade updatePlayerTradeStatus(Integer tradeId, String tradeType,
                                         String tradeStatus, String inOutStatus, String descr) {
        PlayerTrade tradeReq = new PlayerTrade();
        tradeReq.setTradeId(tradeId);
        tradeReq.setTradeStatus(tradeStatus);
        tradeReq.setInOutStatus(inOutStatus);
        tradeReq.setTradeDesc(descr);
        int i = tradeService.updatePlayerTrade(tradeReq);
        if (i > 0){
            return tradeReq;
        }
        return null;
    }


    /**
     * 生成审核记录
     * @param record
     * @return
     */
    @Override
    public TradeVerify createVerify(VerifyReq record){
        TradeVerify verifyReq = new TradeVerify();
        verifyReq.setVerifyStatus(record.getVerifyStatus());
        verifyReq.setVerifyEmpId(record.getEmpId());
        verifyReq.setVerifyDesc(record.getVerifyDesc());
        verifyReq.setVerifyTradeId(record.getTradeId());
        return verifyService.insertTradeVerify(verifyReq);
    }

    /**
     * 玩家账户解冻金额
     * @param playerId
     * @param msg
     * @return
     */
    @Override
    public int unfreezePlayerAccount(String playerId,BigDecimal amount,String amountType, String msg) {
        PlayerAccount accountReq = new PlayerAccount();
        accountReq.setAccPlayerId(playerId);
        //获取玩家账户
        PlayerAccount playerAccount = accountService.getPlayerAccount(accountReq);
        if ("usdtUnfreeze".equalsIgnoreCase(amountType)){
            playerAccount.setAccUsdtFreeze(playerAccount.getAccUsdtFreeze().subtract(amount));
            playerAccount.setAccUsdtAvailable(playerAccount.getAccUsdtAvailable().add(amount));
            playerAccount.setAccUsdt(playerAccount.getAccUsdt().add(amount));
        }
        if ("mtUnfreeze".equalsIgnoreCase(amountType)){
            playerAccount.setAccMtFreeze(playerAccount.getAccMtFreeze().subtract(amount));
            playerAccount.setAccMtAvailable(playerAccount.getAccMtAvailable().add(amount));
            playerAccount.setAccMt(playerAccount.getAccMt().add(amount));
        }
        return accountService.updatePlayerAccount(playerAccount);
    }


    /**
     * 生成交易流水
     * @param payerId
     * @param orderId
     * @param tradeId
     * @param verifyId
     * @param amount
     * @return
     */
    @Override
    @Transactional
    public TradeDetail createTradeDetail(String payerId, Integer orderId, Integer tradeId, Integer verifyId,
                                         BigDecimal amount, String tradeDetailType,String desc){
        TradeDetail tradeDetail = new TradeDetail();
        tradeDetail.setTradeId(tradeId);
        tradeDetail.setVerifyId(verifyId);
        tradeDetail.setTradeDetailType(tradeDetailType);
        tradeDetail.setTradeAmount(amount);
        tradeDetail.setPlayerId(payerId);
        tradeDetail.setDescr(desc);
        tradeDetail.setOrderId(orderId);
        tradeDetail.setVerifyTime(new Date());
        return detailService.insert(tradeDetail);
    }

}
