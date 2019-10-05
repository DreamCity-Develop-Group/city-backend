package com.dream.city.verify.service;


import com.dream.city.base.Result;
import com.dream.city.exception.OperationException;
import com.dream.city.verify.dto.VerifyReq;

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
