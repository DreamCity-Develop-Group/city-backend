package com.dream.city.verify.service.impl;

import com.dream.city.account.entity.PlayerAccount;
import com.dream.city.account.service.AccountService;
import com.dream.city.base.Codes;
import com.dream.city.base.Result;
import com.dream.city.exception.OperationException;
import com.dream.city.trade.dto.PlayerAccountReq;
import com.dream.city.verify.dto.VerifyReq;
import com.dream.city.trade.entity.PlayerEarning;
import com.dream.city.trade.entity.PlayerTrade;
import com.dream.city.trade.enu.AmountDynType;
import com.dream.city.trade.enu.TradeAmountType;
import com.dream.city.verify.enu.VerifyStatus;
import com.dream.city.trade.service.EarningService;
import com.dream.city.trade.service.PlayerTradeService;
import com.dream.city.verify.service.TradeVerifyService;
import com.dream.city.verify.service.VerifyCommonService;
import com.dream.city.verify.service.WithdrawVerifyHandleService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;

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
    public Result withdrawSubscribe(VerifyReq record) throws OperationException {
        return null;
    }

    @Override
    @Transactional
    public Result withdrawVerify(VerifyReq record) throws OperationException {
        boolean success = Boolean.FALSE;
        String msg = "";
        if (record.getTradeId() == null || (record.getTradeId() != null &&record.getTradeId() < 0)){
            msg = "交易id不能为空";
        }
        //提现收益
        PlayerTrade trade = tradeService.getPlayerTradeById(record.getTradeId());
        PlayerEarning earningReq = new PlayerEarning();
        earningReq.setEarnPlayerId(trade.getTradePlayerId());
        PlayerEarning earning = earningService.getEarning(earningReq);

        //玩家账户信息
        PlayerAccount accountReq = new PlayerAccount();
        accountReq.setAccPlayerId(trade.getTradePlayerId());
        PlayerAccount playerAccount = accountService.getPlayerAccount(accountReq);

        //校验金额
        Result<Boolean> checkWithdrawAmountResult = this.checkWithdrawAmount(trade.getTradeAmount(),earning.getEarnTax(),playerAccount);
        success = checkWithdrawAmountResult.getSuccess();
        msg = checkWithdrawAmountResult.getMsg();
        if (StringUtils.isNotBlank(msg)){
            return new Result<Boolean>(success,msg);
        }

        //生成审核记录
        verifyCommonService.createVerify (record);

        if (record.getVerifyStatus().equalsIgnoreCase(VerifyStatus.pass.name())){
            //审核通过
            //玩家账户扣除金额 扣冻结Usdt金额
            int updatePlayerAccount = verifyCommonService.playerSubtractUsdtFreeze(trade.getTradePlayerId(), trade.getTradeAmount());

            //玩家账户扣除Usdt金额流水
            PlayerTrade createPlayerTrade = null;
            if (updatePlayerAccount > 0){
                PlayerAccountReq createPlayerTradeReq = new PlayerAccountReq();
                createPlayerTradeReq.setAccId(playerAccount.getAccId());
                createPlayerTradeReq.setAccPlayerId(playerAccount.getAccPlayerId());
                createPlayerTradeReq.setTradeType(TradeAmountType.WITHDRAW.name());
                createPlayerTradeReq.setTradeType(AmountDynType.out.name());
                createPlayerTrade = verifyCommonService.playerSubtractUsdtFreezeTrade(createPlayerTradeReq,trade.getTradeAmount(),"提现扣除Usdt金额");
            }

            //更新玩家收益为已经提取
            int updateEarning = 0;
            if (createPlayerTrade != null){
                earning.setIsWithdrew("Y");
                updateEarning = earningService.updateEarningById(earning);
            }else {
                msg = "审核新增流水失败";
            }

            if(updateEarning > 0){
                //玩家账户扣除税金 扣冻结mt税金
                Result<Integer> updatePlayerAccountMt = this.subtractPlayerAccountMt(earning, playerAccount, msg);
                success = updatePlayerAccountMt.getSuccess();
                msg = updatePlayerAccountMt.getMsg();
                updatePlayerAccount = updatePlayerAccountMt.getData();
            }else {
                msg = "更新玩家收益为已经提取";
            }

            //玩家账户扣除税金流水
            PlayerTrade createPlayerTradeMt = null;
            if (updatePlayerAccount > 0){
                Result<PlayerTrade> createPlayerTradeMtResult = this.createPlayerTrade(earning, playerAccount, msg);
                createPlayerTradeMt = createPlayerTradeMtResult.getData();
            }else {
                msg = "审核更新玩家mt账户失败";
            }

            //把玩家账户扣除jine 加到平台账户
            PlayerAccount platformAccount = null;
            if (createPlayerTrade != null && createPlayerTrade.getTradeId() != null && createPlayerTrade.getTradeId() > 0){
                Result<PlayerAccount> updatePlatformAccountResult = this.updatePlatformAccount(earning, msg);
                platformAccount = updatePlatformAccountResult.getData();
                success = updatePlatformAccountResult.getSuccess();
                msg = updatePlatformAccountResult.getMsg();
            }
            //平台账户增加金额流水
            if (platformAccount != null){
                Result<PlayerTrade> createPlatformATradeResult  = this.createPlatformTrade(platformAccount,trade.getTradeAmount(),TradeAmountType.USDT_INVEST_TAX.name(),msg) ;
                success = createPlatformATradeResult.getSuccess();
                msg = createPlatformATradeResult.getMsg();
            }

        }else if (record.getVerifyStatus().equalsIgnoreCase(VerifyStatus.notpass.name())){
            //审核不通过
            success = Boolean.TRUE;
            msg = "审核成功！";

            //返回冻结金额
            Result<Integer> unfreezePlayerAccount = this.unfreezePlayerAccount(earning, playerAccount, msg);
            if (unfreezePlayerAccount.getData() != null && unfreezePlayerAccount.getData() < 1){
                msg = "审核不通过解冻资金失败！";
            }
        }

        return new Result<Boolean>(success,msg,success);
    }


    /**
     * 平台账户增加流水
     * @param platformAccount
     * @param msg
     * @return
     */
    private Result<PlayerTrade> createPlatformTrade(PlayerAccount platformAccount,BigDecimal amount,String tradeAmountType,String msg) throws OperationException {
        boolean success = Boolean.TRUE;
        //平台mt账户增加税金
        PlayerAccountReq createPlayerTradeReq = new PlayerAccountReq();
        createPlayerTradeReq.setAccId(platformAccount.getAccId());
        createPlayerTradeReq.setAccPlayerId(platformAccount.getAccPlayerId());
        //createPlayerTradeReq.setTradeAmountType(TradeAmountType.USDT_INVEST_TAX.name());
        createPlayerTradeReq.setTradeAmountType(tradeAmountType);
        createPlayerTradeReq.setTradeType(AmountDynType.in.name());
        Result<PlayerTrade> createPlatformATradeResult = null;
        PlayerTrade createPlayerTrade = null;
        try {
            //新增平台mt账户增加税金流水
            createPlatformATradeResult = this.createPlayerTrade(createPlayerTradeReq, amount, "平台mt账户增加税金");
            if (createPlatformATradeResult.getSuccess()) {
                createPlayerTrade = createPlatformATradeResult.getData();
            }
        } catch (Exception e) {
            msg = "审核新增扣除税金流水异常";
            logger.error(msg, e);
            throw new OperationException(Codes.SYSTEM_ERROR.getCode(),"审核新增扣除税金流水异常");
        }
        if (!createPlatformATradeResult.getSuccess() && createPlayerTrade == null) {
            msg = "审核新增扣除税金流水失败";
        } else {
            success = Boolean.TRUE;
            msg = "审核成功！";
        }
        return new Result<PlayerTrade>(success,msg,createPlayerTrade);
    }

    /**
     * 把玩家账户扣除金额 加到平台账户
     * @param earning
     * @param msg
     * @return
     */
    private Result<PlayerAccount> updatePlatformAccount(PlayerEarning earning,String msg) throws OperationException {
        //获取平台账户
        List<PlayerAccount> platformAccountList = null;
        PlayerAccount platformAccount = null;
        platformAccountList = accountService.getPlatformAccounts(null);
        if (!CollectionUtils.isEmpty(platformAccountList)){
            platformAccount = platformAccountList.get(0);
        }
        int updatePlayerAccount = 0;
        if (platformAccount != null){
            //把玩家账户扣除税金 加到平台mt账户
            platformAccount.setAccMt(platformAccount.getAccMtAvailable().add(earning.getEarnTax()));
            platformAccount.setAccMtAvailable(platformAccount.getAccMtAvailable().add(earning.getEarnTax()));
            try {
                //更新平台mt账户
                updatePlayerAccount = accountService.updatePlayerAccount(platformAccount);
            }catch (Exception e){
                msg = "审核平台mt账户异常";
                logger.error(msg,e);
                throw new OperationException(Codes.SYSTEM_ERROR.getCode(),"审核更新平台mt账户异常");
            }
        }
        if (updatePlayerAccount> 0){
            return new Result<PlayerAccount>(Boolean.TRUE,msg,platformAccount);
        }
        return new Result<PlayerAccount>(Boolean.FALSE,msg,null);
    }

    /**
     * 玩家账户扣除税金流水
     * @param earning
     * @param playerAccount
     * @param msg
     * @return
     */
    private Result<PlayerTrade> createPlayerTrade(PlayerEarning earning,PlayerAccount playerAccount,String msg) throws OperationException {
        Result<PlayerTrade> createPlayerTradeResult = null;
        //玩家账户扣除税金流水
        PlayerAccountReq createPlayerTradeReq = new PlayerAccountReq();
        createPlayerTradeReq.setAccId(playerAccount.getAccId());
        createPlayerTradeReq.setAccPlayerId(playerAccount.getAccPlayerId());
        createPlayerTradeReq.setTradeAmountType(TradeAmountType.USDT_INVEST_TAX.name());
        createPlayerTradeReq.setTradeType(AmountDynType.out.name());
        PlayerTrade createPlayerTrade = null;
        try {
            //新增扣除税金流水
            createPlayerTradeResult = this.createPlayerTrade(createPlayerTradeReq, earning.getEarnTax(), "审核扣除mt税金");
            if (createPlayerTradeResult.getSuccess()){
                createPlayerTrade = createPlayerTradeResult.getData();
            }
        }catch (Exception e){
            msg = "审核新增扣除税金流水异常";
            logger.error("msg",e);
            throw new OperationException(Codes.SYSTEM_ERROR.getCode(),"审核新增扣除税金流水异常");
        }
        return new Result<PlayerTrade>(Boolean.TRUE,msg,createPlayerTrade);
    }

    /**
     * 玩家账户扣除税金 扣冻结mt税金
     * @param earning
     * @param playerAccount
     * @param msg
     * @return
     */
    private Result<Integer> subtractPlayerAccountMt(PlayerEarning earning,PlayerAccount playerAccount,String msg) throws OperationException {
        //玩家账户扣除税金 扣冻结mt税金
        PlayerAccount updateAccountReq = new PlayerAccount();
        updateAccountReq.setAccId(playerAccount.getAccId());
        updateAccountReq.setAccPlayerId(playerAccount.getAccPlayerId());
        updateAccountReq.setAccMtFreeze(playerAccount.getAccMtFreeze().subtract(earning.getEarnTax()));
        int updatePlayerAccount = 0;
        try {
            //更新玩家账户
            updatePlayerAccount = accountService.updatePlayerAccount(updateAccountReq);
        }catch (Exception e){
            msg = "审核更新玩家mt账户异常";
            logger.error(msg,e);
            throw new OperationException(Codes.SYSTEM_ERROR.getCode(),"审核更新玩家mt账户异常");
        }
        return new Result<Integer>(Boolean.TRUE,msg,updatePlayerAccount);
    }

    /**
     * 玩家账户扣除金额流水
     * @param earning
     * @param playerAccount
     * @param msg
     * @return
     */
    private Result<PlayerTrade> createPlayerTradeResult(PlayerEarning earning,PlayerAccount playerAccount,String msg) throws OperationException {
        //玩家账户扣除金额流水
        Result<PlayerTrade> createPlayerTradeResult = null;
        PlayerTrade createPlayerTrade = null;
        PlayerAccountReq createPlayerTradeReq = new PlayerAccountReq();

        createPlayerTradeReq.setAccId(playerAccount.getAccId());
        createPlayerTradeReq.setAccPlayerId(playerAccount.getAccPlayerId());
        createPlayerTradeReq.setTradeType(TradeAmountType.WITHDRAW.name());
        createPlayerTradeReq.setTradeType(AmountDynType.out.name());
        try {
            //新增流水
            createPlayerTradeResult = this.createPlayerTrade(createPlayerTradeReq, earning.getEarnMax(), "审核扣除冻结usdt");
            if (createPlayerTradeResult != null && createPlayerTradeResult.getSuccess()){
                createPlayerTrade = createPlayerTradeResult.getData();
            }
        }catch (Exception e){
            msg = "审核新增流水异常";
            logger.error(msg,e);
            throw new OperationException(Codes.SYSTEM_ERROR.getCode(),"审核新增流水异常");
        }
        return new Result<PlayerTrade>(Boolean.TRUE,msg,createPlayerTrade);
    }

    /**
     * 玩家账户扣除金额 扣冻结金额
     * @param earning
     * @param playerAccount
     * @param msg
     * @return
     */
    private Result<Integer> subtractPlayerAccount(PlayerEarning earning,PlayerAccount playerAccount,String msg) throws OperationException {
        //玩家账户扣除金额 扣冻结金额
        PlayerAccount updateAccountReq = new PlayerAccount();
        updateAccountReq.setAccId(playerAccount.getAccId());
        updateAccountReq.setAccPlayerId(playerAccount.getAccPlayerId());
        updateAccountReq.setAccUsdtFreeze(playerAccount.getAccUsdtFreeze().subtract(earning.getEarnMax()));
        int updatePlayerAccount = 0;
        try {
            //更新玩家账户
            updatePlayerAccount = accountService.updatePlayerAccount(updateAccountReq);
        }catch (Exception e){
            msg = "审核更新usdt账户异常";
            logger.error(msg,e);
            throw new OperationException(Codes.SYSTEM_ERROR.getCode(),"审核更新usdt账户异常");
        }
        return new Result<Integer>(Boolean.TRUE,msg,updatePlayerAccount);
    }

    /**
     * 审核不通过解冻金额
     * @param earning
     * @param playerAccount
     * @param msg
     * @return
     */
    private Result<Integer> unfreezePlayerAccount(PlayerEarning earning,PlayerAccount playerAccount,String msg) throws OperationException {
        //玩家账户扣除金额 扣冻结金额
        PlayerAccount updateAccountReq = new PlayerAccount();
        updateAccountReq.setAccId(playerAccount.getAccId());
        updateAccountReq.setAccPlayerId(playerAccount.getAccPlayerId());
        updateAccountReq.setAccUsdtFreeze(playerAccount.getAccUsdtFreeze().subtract(earning.getEarnMax()));
        updateAccountReq.setAccUsdtAvailable(playerAccount.getAccUsdtAvailable().add(earning.getEarnMax()));
        updateAccountReq.setAccUsdt(playerAccount.getAccUsdt().add(earning.getEarnMax()));
        updateAccountReq.setAccMtFreeze(playerAccount.getAccMtFreeze().subtract(earning.getEarnTax()));
        updateAccountReq.setAccMtFreeze(playerAccount.getAccMtFreeze().add(earning.getEarnTax()));
        updateAccountReq.setAccMt(playerAccount.getAccMt().add(earning.getEarnTax()));
        int updatePlayerAccount = 0;
        try {
            //更新玩家账户
            updatePlayerAccount = accountService.updatePlayerAccount(updateAccountReq);
        }catch (Exception e){
            msg = "审核不通过解冻金额异常";
            logger.error(msg,e);
            throw new OperationException(Codes.SYSTEM_ERROR.getCode(),"审核不通过解冻金额异常");
        }
        return new Result<Integer>(Boolean.TRUE,msg,updatePlayerAccount);
    }



    /**
     * 校验提现金额
     * @param tradeAmount
     * @param playerAccount
     * @return
     */
    private Result<Boolean> checkWithdrawAmount(BigDecimal tradeAmount,BigDecimal earnTa,PlayerAccount playerAccount){
        String msg = "";
        //校验金额
        if (playerAccount.getAccUsdtFreeze().compareTo(tradeAmount) < 0){
            if (playerAccount.getAccUsdtAvailable().compareTo(tradeAmount) < 0){
                msg = "USDT不足";
                return new Result<Boolean>(Boolean.FALSE,"USDT不足");
            }
        }
        //校验税金
        if (playerAccount.getAccMtFreeze().compareTo(earnTa) < 0){
            if (playerAccount.getAccMtAvailable().compareTo(earnTa) < 0){
                msg = "MT不足";
                return new Result<Boolean>(Boolean.FALSE,"MT不足");
            }
        }
        return new Result<Boolean>(Boolean.TRUE,"");
    }


    /**
     * 判断是内部还是外部地址
     * @param accAddr 玩家账户地址
     * @return TRUE：内部地址，FALSE外部地址
     */
    private boolean isInsideAccAddr(String accAddr){
        //判断accAddr是内部还是外部地址 todo

        return Boolean.TRUE;
    }


    /**
     * 新增交易记录
     * @param record
     */
    private Result<PlayerTrade> createPlayerTrade(PlayerAccountReq record,BigDecimal tradeAmount,String desc) throws Exception{
        PlayerTrade tradeReq = new PlayerTrade();
        tradeReq.setTradeType(record.getTradeType());
        tradeReq.setTradeAmountType(record.getTradeType());
        tradeReq.setTradeDesc(desc);
        tradeReq.setTradeAccId(record.getAccId());
        tradeReq.setTradePlayerId(record.getAccPlayerId());
        tradeReq.setTradeAmount(tradeAmount);
        //生成交易记录
        PlayerTrade insertPlayerTrade = tradeService.insertPlayerTrade(tradeReq);
        boolean success = Boolean.FALSE;
        if (insertPlayerTrade != null){
            success = Boolean.TRUE;
        }
        return new Result(success,desc,insertPlayerTrade);
    }



}
