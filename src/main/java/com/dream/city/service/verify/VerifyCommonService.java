package com.dream.city.service.verify;


import com.dream.city.base.model.entity.PlayerAccount;
import com.dream.city.base.model.entity.PlayerTrade;
import com.dream.city.base.model.req.PlayerAccountReq;
import com.dream.city.base.model.req.VerifyReq;

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
    int playerSubtractAmount(String accPlayerId,BigDecimal amount,String amountType);


    /**
     * 平台账户增加金额
     * @param amount
     * @return
     */
    PlayerAccount platformAddAmount(BigDecimal amount, String amountType);


    /**
     * 生成交易流水
     * @param playerAccount
     * @return
     */
    PlayerTrade createTradeRecord(PlayerAccountReq playerAccount, BigDecimal amount, String desc);


    /**
     * 生成审核记录
     * @param record
     * @return
     */
    int createVerify (VerifyReq record);


}
