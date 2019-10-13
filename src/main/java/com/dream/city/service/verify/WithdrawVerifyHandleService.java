package com.dream.city.service.verify;


import com.dream.city.base.Result;
import com.dream.city.base.model.req.VerifyReq;

/**
 *  提现审核
 * @author
 */
public interface WithdrawVerifyHandleService {


    /**
     * 提现审核
     * @param record
     * @return
     */
    Result<Boolean> withdrawVerify(VerifyReq record) ;
















    /**
     * 提现预约
     * @param record
     * @return
     */
    @Deprecated
    Result withdrawSubscribe(VerifyReq record) ;



}
