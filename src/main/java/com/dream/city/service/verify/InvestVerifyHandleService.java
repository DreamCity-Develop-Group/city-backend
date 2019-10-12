package com.dream.city.service.verify;


import com.dream.city.base.Result;
import com.dream.city.base.model.req.VerifyReq;

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
    @Deprecated
    Result investOrderVerify(VerifyReq verifyReq);

}
