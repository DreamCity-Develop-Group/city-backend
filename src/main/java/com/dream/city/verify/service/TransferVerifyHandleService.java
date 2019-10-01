package com.dream.city.verify.service;


import com.dream.city.base.Result;
import com.dream.city.exception.OperationException;
import com.dream.city.verify.dto.VerifyReq;

/**
 *  转账审核
 */
public interface TransferVerifyHandleService {


    /**
     * 转账审核
     * @param record
     * @return
     */
    Result transferVerify(VerifyReq record) throws OperationException;



}
