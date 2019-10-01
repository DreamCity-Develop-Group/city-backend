package com.dream.city.trade.service.impl;

import com.dream.city.account.entity.PlayerAccount;
import com.dream.city.account.service.AccountService;
import com.dream.city.base.Codes;
import com.dream.city.base.Result;
import com.dream.city.exception.OperationException;
import com.dream.city.trade.dto.PlayerAccountReq;
import com.dream.city.trade.dto.VerifyReq;
import com.dream.city.trade.entity.PlayerEarning;
import com.dream.city.trade.entity.PlayerTrade;
import com.dream.city.trade.entity.TradeVerify;
import com.dream.city.trade.enu.AmountDynType;
import com.dream.city.trade.enu.TradeAmountType;
import com.dream.city.trade.enu.VerifyStatus;
import com.dream.city.trade.service.EarningService;
import com.dream.city.trade.service.PlayerTradeService;
import com.dream.city.trade.service.TradeVerifyService;
import com.dream.city.trade.service.WithdrawVerifyHandleService;
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



    @Override
    @Transactional
    public Result withdrawVerify(VerifyReq record) throws OperationException {
        boolean success = Boolean.FALSE;
        String msg = "";
        if (record.getEarnId() == null || (record.getEarnId() != null &&record.getEarnId() < 0)){
            msg = "收益id不能为空";
            return new Result(Codes.PARAM_ERROR.getCode(),"收益id不能为空");
        }
        //提现收益
        PlayerEarning earning = earningService.getEarning(record.getEarnId());

        //玩家账户信息
        PlayerAccount accountReq = new PlayerAccount();
        accountReq.setAccPlayerId(earning.getEarnPlayerId());
        PlayerAccount playerAccount = accountService.getPlayerAccount(accountReq);

        //校验金额
        Result<Boolean> checkWithdrawAmountResult = this.checkWithdrawAmount(earning,playerAccount);
        success = checkWithdrawAmountResult.getSuccess();
        msg = checkWithdrawAmountResult.getMsg();

        //审核交易 修改审核状态
        int updateTradeVerify = this.updateTradeVerify(record, msg);

        if (updateTradeVerify > 0){
            if (record.getVerifyStatus().equalsIgnoreCase(VerifyStatus.pass.name())){
                //审核通过
                //玩家账户扣除金额 扣冻结金额
                Result<Integer> updatePlayerAccountResult = this.subtractPlayerAccount(earning, playerAccount, msg);
                int updatePlayerAccount = updatePlayerAccountResult.getData();
                msg = updatePlayerAccountResult.getMsg();
                success = updatePlayerAccountResult.getSuccess();

                //玩家账户扣除金额流水
                Result<PlayerTrade> createPlayerTradeResult = null;
                PlayerTrade createPlayerTrade = null;
                if (updatePlayerAccount > 0){
                    createPlayerTradeResult = this.createPlayerTradeResult(earning,playerAccount,msg);
                    createPlayerTrade = createPlayerTradeResult.getData();
                    msg = updatePlayerAccountResult.getMsg();
                    success = createPlayerTradeResult.getSuccess();
                }

                //更新玩家收益为已经提取
                int updateEarning = 0;
                if (createPlayerTradeResult != null && createPlayerTradeResult.getSuccess() && createPlayerTrade != null){
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

                    //玩家账户扣除税金流水
                    createPlayerTradeResult = null;
                    if (updatePlayerAccount > 0){
                        createPlayerTradeResult = this.createPlayerTradeMt(earning, playerAccount,msg);
                    }else {
                        msg = "审核更新玩家mt账户失败";
                    }

                    //获取平台账户
                    PlayerAccount platformAccount = null;
                    if (createPlayerTradeResult.getSuccess() && createPlayerTrade != null){
                        Result<PlayerAccount> updatePlatformAccountResult = this.updatePlatformAccount(earning, msg);
                        platformAccount = updatePlatformAccountResult.getData();
                        success = updatePlatformAccountResult.getSuccess();
                        msg = updatePlatformAccountResult.getMsg();
                    }

                    //平台mt账户增加税金
                    if (platformAccount != null && success){
                        Result<PlayerTrade> createPlatformATradeResult = null;
                        createPlatformATradeResult = this.createPlatformTrade(earning,platformAccount, msg);
                        success = createPlatformATradeResult.getSuccess();
                        msg = createPlatformATradeResult.getMsg();
                    }
                }else {
                    msg = "更新玩家收益为已经提取";
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
        }else {
            msg = "修改审核状态失败！";
        }
        return new Result<Boolean>(success,msg,success);
    }


    /**
     * 平台mt账户增加税金
     * @param earning
     * @param platformAccount
     * @param msg
     * @return
     */
    private Result<PlayerTrade> createPlatformTrade(PlayerEarning earning,PlayerAccount platformAccount,String msg) throws OperationException {
        boolean success = Boolean.TRUE;
        //平台mt账户增加税金
        PlayerAccountReq createPlayerTradeReq = new PlayerAccountReq();
        createPlayerTradeReq.setAccId(platformAccount.getAccId());
        createPlayerTradeReq.setAccPlayerId(platformAccount.getAccPlayerId());
        createPlayerTradeReq.setTradeAmountType(TradeAmountType.USDT_INVEST_TAX.name());
        createPlayerTradeReq.setTradeType(AmountDynType.in.name());
        Result<PlayerTrade> createPlatformATradeResult = null;
        PlayerTrade createPlayerTrade = null;
        try {
            //新增平台mt账户增加税金流水
            createPlatformATradeResult = this.createPlayerTrade(createPlayerTradeReq, earning.getEarnTax(), "平台mt账户增加税金");
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
     * 获取平台账户
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
    private Result<PlayerTrade> createPlayerTradeMt(PlayerEarning earning,PlayerAccount playerAccount,String msg) throws OperationException {
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
     * 审核交易 修改审核状态
     * @param record
     * @param msg
     * @return
     */
    private int updateTradeVerify (VerifyReq record,String msg){
        //审核交易 修改审核状态
        int updateTradeVerify = 0;
        if(StringUtils.isBlank(msg)){
            TradeVerify verifyReq = new TradeVerify();
            verifyReq.setVerifyStatus(record.getVerifyStatus());
            verifyReq.setVerifyEmpId(record.getEmpId());
            verifyReq.setVerifyDesc(record.getVerifyDesc());
            verifyReq.setVerifyTradeId(record.getTradeId());
            updateTradeVerify = verifyService.updateTradeVerify(verifyReq);
        }
        return updateTradeVerify;
    }

    /**
     * 校验提现金额
     * @param earning
     * @param playerAccount
     * @return
     */
    private Result<Boolean> checkWithdrawAmount(PlayerEarning earning,PlayerAccount playerAccount){
        String msg = "";
        //校验金额
        if (playerAccount.getAccUsdtFreeze().compareTo(earning.getEarnMax()) < 0){
            if (playerAccount.getAccUsdtAvailable().compareTo(earning.getEarnMax()) < 0){
                msg = "USDT不足";
                return new Result<Boolean>(Boolean.FALSE,"USDT不足");
            }
        }
        //校验税金
        if (playerAccount.getAccMtFreeze().compareTo(earning.getEarnTax()) < 0){
            if (playerAccount.getAccMtAvailable().compareTo(earning.getEarnTax()) < 0){
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
