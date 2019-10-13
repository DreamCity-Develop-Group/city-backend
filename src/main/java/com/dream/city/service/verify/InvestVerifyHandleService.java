package com.dream.city.service.verify;


import com.dream.city.base.Result;
import com.dream.city.base.exception.BusinessException;
import com.dream.city.base.model.req.VerifyReq;
import com.dream.city.exception.OperationException;

/**
 *  投资审核
 */
public interface InvestVerifyHandleService {


    /**
     * 预约审核
     * @param verifyReq
     * @return
     */
    Result subscribeOrderVerify(VerifyReq verifyReq) throws BusinessException;


    /**
     * 投资审核
     * @param verifyReq
     * @return
     */
    @Deprecated
    Result investOrderVerify(VerifyReq verifyReq);

}
