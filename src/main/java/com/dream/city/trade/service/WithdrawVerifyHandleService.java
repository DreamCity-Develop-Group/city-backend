package com.dream.city.trade.service;


import com.dream.city.base.Result;
import com.dream.city.exception.OperationException;
import com.dream.city.trade.dto.VerifyReq;

/**
 *  提现审核
 */
public interface WithdrawVerifyHandleService {


    /**
     * 提现审核
     * @param record
     * @return
     */
    Result withdrawVerify(VerifyReq record) throws OperationException;


}
