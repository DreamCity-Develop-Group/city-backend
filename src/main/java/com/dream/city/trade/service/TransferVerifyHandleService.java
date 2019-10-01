package com.dream.city.trade.service;


import com.dream.city.base.Result;
import com.dream.city.exception.OperationException;
import com.dream.city.trade.dto.VerifyReq;

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
