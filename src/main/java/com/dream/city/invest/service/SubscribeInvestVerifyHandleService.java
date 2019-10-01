package com.dream.city.invest.service;


import com.dream.city.base.Result;
import com.dream.city.exception.OperationException;
import com.dream.city.trade.dto.VerifyReq;

/**
 *  投资审核
 */
public interface SubscribeInvestVerifyHandleService {



    /**
     * 预约投资
     * @param record
     * @return
     */
    Result subscribeInvestVerify(VerifyReq record) throws OperationException;


}
