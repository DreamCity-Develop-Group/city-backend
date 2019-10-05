package com.dream.city.verify.service.impl;

import com.dream.city.account.entity.PlayerAccount;
import com.dream.city.account.service.AccountService;
import com.dream.city.trade.dto.PlayerAccountReq;
import com.dream.city.trade.entity.PlayerTrade;
import com.dream.city.trade.enu.AmountDynType;
import com.dream.city.trade.enu.TradeAmountType;
import com.dream.city.trade.service.EarningService;
import com.dream.city.trade.service.PlayerTradeService;
import com.dream.city.verify.dto.VerifyReq;
import com.dream.city.verify.entity.TradeVerify;
import com.dream.city.verify.service.TradeVerifyService;
import com.dream.city.verify.service.VerifyCommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;

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

    @Override
    @Transactional
    public int playerSubtractUsdtFreeze(String accPlayerId,BigDecimal amount) {
        PlayerAccount getPlayerAccountReq = new PlayerAccount();
        getPlayerAccountReq.setAccPlayerId(accPlayerId);
        PlayerAccount playerAccount = accountService.getPlayerAccount(getPlayerAccountReq);
        //玩家账户扣除金额 扣冻结Usdt金额
        PlayerAccount updateAccountReq = new PlayerAccount();
        updateAccountReq.setAccPlayerId(playerAccount.getAccPlayerId());
        updateAccountReq.setAccUsdtFreeze(playerAccount.getAccUsdtFreeze().subtract(amount));
        return accountService.updatePlayerAccount(updateAccountReq);
    }

    @Override
    public PlayerTrade playerSubtractUsdtFreezeTrade(PlayerAccountReq playerAccount,BigDecimal amount,String desc) {
        //新增流水
        PlayerTrade tradeReq = new PlayerTrade();
        tradeReq.setTradeType(playerAccount.getTradeType());
        tradeReq.setTradeAmountType(playerAccount.getTradeType());
        tradeReq.setTradeAccId(playerAccount.getAccId());
        tradeReq.setTradePlayerId(playerAccount.getAccPlayerId());
        tradeReq.setTradeDesc(desc);
        tradeReq.setTradeAmount(amount);
        //生成交易记录
        return tradeService.insertPlayerTrade(tradeReq);
    }

    @Override
    public PlayerAccount platformAddUsdt(BigDecimal amount) {
        //获取平台账户
        List<PlayerAccount>  platformAccountList = accountService.getPlatformAccounts(null);
        PlayerAccount platformAccount = null;
        if (!CollectionUtils.isEmpty(platformAccountList)){
            platformAccount = platformAccountList.get(0);
        }
        int updatePlayerAccount = 0;
        if (platformAccount != null){
            //把玩家账户扣除usdt 加到平台usdt账户
            platformAccount.setAccUsdt(platformAccount.getAccUsdt().add(amount));
            platformAccount.setAccUsdtAvailable(platformAccount.getAccUsdtAvailable().add(amount));
            //更新平台usdt账户
            updatePlayerAccount = accountService.updatePlayerAccount(platformAccount);
        }
        if (updatePlayerAccount> 0){
            return null;
        }
        return platformAccount;
    }

    @Override
    public int platformAddUsdtTrade(PlayerAccount playerAccount,BigDecimal amount) {
        return 0;
    }


    /**
     * 生成审核记录
     * @param record
     * @return
     */
    @Override
    public int createVerify (VerifyReq record){
        //生成审核记录
        TradeVerify verifyReq = new TradeVerify();
        verifyReq.setVerifyStatus(record.getVerifyStatus());
        verifyReq.setVerifyEmpId(record.getEmpId());
        verifyReq.setVerifyDesc(record.getVerifyDesc());
        verifyReq.setVerifyTradeId(record.getTradeId());
        return verifyService.updateTradeVerify(verifyReq);
    }
}
