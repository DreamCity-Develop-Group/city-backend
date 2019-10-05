package com.dream.city.verify.service;


import com.dream.city.base.Result;
import com.dream.city.verify.dto.VerifyReq;

/**
 *  投资审核
 */
public interface InvestVerifyHandleService {


    /**
     * 预约审核
     * @param verifyReq
     * @return
     */
    Result subscribeOrderVerify(VerifyReq verifyReq);


    /**
     * 投资审核
     * @param verifyReq
     * @return
     */
    Result investOrderVerify(VerifyReq verifyReq);

}
