package com.dream.city.service.verify;


import com.dream.city.base.model.entity.*;
import com.dream.city.base.model.req.VerifyReq;

import java.math.BigDecimal;


/**
 * 审核公共方法
 */
public interface VerifyCommonService {

    /**
     * 扣除玩家金额
     * @param accPlayerId
     * @param amountType
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
     * 生成交易
     * @param payerId
     * @return
     */
    PlayerTrade createPlayerTrade(String payerId,Integer orderId,PlayerTrade playerTrade,String tradeType,
                                  String tradeStatus,String inOutStatus,String descr);


    PlayerTrade updatePlayerTradeStatus(Integer tradeId,String tradeType,
                                  String tradeStatus,String inOutStatus,String descr);

    /**
     * 生成交易流水
     * @param payerId
     * @return
     */
    TradeDetail createTradeDetail(String payerId, Integer orderId, Integer tradeId, Integer verifyId,
                                  BigDecimal amount,String tradeDetailType,String desc);


    /**
     * 生成审核记录
     * @param record
     * @return
     */
    TradeVerify createVerify (VerifyReq record);


    /**
     * 玩家账户解冻金额
     * @param playerId
     * @param msg
     * @return
     */
    int unfreezePlayerAccount(String playerId,BigDecimal amount,String amountType, String msg);
}
