package com.dream.city.verify.service;


import com.dream.city.account.entity.PlayerAccount;
import com.dream.city.trade.dto.PlayerAccountReq;
import com.dream.city.trade.entity.PlayerTrade;
import com.dream.city.verify.dto.VerifyReq;

import java.math.BigDecimal;


/**
 * 审核公共方法
 */
public interface VerifyCommonService {

    /**
     * 扣除玩家金额
     * @param accPlayerId
     * @return
     */
    int playerSubtractUsdtFreeze(String accPlayerId,BigDecimal accUsdtFreeze);

    /**
     * 玩家Usdt交易流水
     * @param playerAccount
     * @return
     */
    PlayerTrade playerSubtractUsdtFreezeTrade(PlayerAccountReq playerAccount, BigDecimal accUsdtFreeze, String desc);




    /**
     * 平台账户增加金额
     * @param accUsdt
     * @return
     */
    PlayerAccount platformAddUsdt(BigDecimal accUsdt);

    /**
     * 平台Usdt交易流水
     * @param playerAccount
     * @return
     */
    int platformAddUsdtTrade(PlayerAccount playerAccount, BigDecimal accUsdt);


    /**
     * 生成审核记录
     * @param record
     * @return
     */
    int createVerify (VerifyReq record);


}
