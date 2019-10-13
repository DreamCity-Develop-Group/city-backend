package com.dream.city.service.verify;


import com.dream.city.base.Result;
import com.dream.city.base.model.req.VerifyReq;

/**
 *  转账审核
 *  外部转账 流程跟提现审核一样
 */
public interface TransferVerifyHandleService {


    /**
     * 转账审核
     * @param record
     * @return
     */
    @Deprecated
    Result transferVerify(VerifyReq record) ;



}
