package com.dream.city.verify.service;


import com.dream.city.base.Result;
import com.dream.city.base.model.req.VerifyReq;
import com.dream.city.exception.OperationException;

/**
 *  提现审核
 */
public interface WithdrawVerifyHandleService {


    /**
     * 提现预约
     * @param record
     * @return
     */
    Result withdrawSubscribe(VerifyReq record) throws OperationException;

    /**
     * 提现审核
     * @param record
     * @return
     */
    Result withdrawVerify(VerifyReq record) throws OperationException;


}
