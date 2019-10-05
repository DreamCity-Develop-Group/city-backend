package com.dream.city.verify.service.impl;

import com.dream.city.base.Result;
import com.dream.city.invest.entity.Order;
import com.dream.city.invest.enu.InvestStatus;
import com.dream.city.invest.service.OrderService;
import com.dream.city.verify.dto.VerifyReq;
import com.dream.city.verify.entity.TradeVerify;
import com.dream.city.verify.enu.VerifyType;
import com.dream.city.verify.service.InvestVerifyHandleService;
import com.dream.city.verify.service.TradeVerifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InvestVerifyHandleServiceImpl implements InvestVerifyHandleService {


    @Autowired
    TradeVerifyService verifyService;
    @Autowired
    OrderService orderService;


    @Override
    @Transactional
    public Result subscribeOrderVerify(VerifyReq verifyReq) {
        boolean success = Boolean.FALSE;
        String descr = "预约审核失败";
        //审核将状态改成投资
        int i = this.updateOrderState(verifyReq.getOrderId(), InvestStatus.INVEST.name());
        if (i > 0){
            success = Boolean.TRUE;
            descr = "预约审核成功";
            //生成审核记录
            this.insertTradeVerify(verifyReq);
        }
        return new Result(success,descr,i);
    }

    @Override
    public Result investOrderVerify(VerifyReq verifyReq) {
        boolean success = Boolean.FALSE;
        String descr = "投资审核失败";
        //审核成功，将状态改成经营中
        int i = this.updateOrderState(verifyReq.getOrderId(), InvestStatus.MANAGEMENT.name());
        if (i > 0){
            success = Boolean.TRUE;
            descr = "投资审核成功";
            //生成审核记录
            this.insertTradeVerify(verifyReq);

            String tradeType = VerifyType.INVEST.name();
            //玩家账户扣除投资金额


            //玩家账户扣除投资金额流水



            //平台账户增加投资金额


            //平台账户增加投资金额流水

        }
        return new Result(success,descr,i);
    }



    private int insertTradeVerify(VerifyReq verifyReq){
        TradeVerify record = new TradeVerify();
        record.setVerifyOrderId(verifyReq.getOrderId());
        record.setVerifyStatus(verifyReq.getVerifyStatus());
        record.setVerifyDesc(verifyReq.getVerifyDesc());
        record.setVerifyEmpId(verifyReq.getEmpId());
        return verifyService.insertTradeVerify(record);
    }


    private int updateOrderState(Integer orderId,String orderState){
        Order orderReq = new Order();
        orderReq.setOrderId(orderId);
        orderReq.setOrderState(orderState);
        return orderService.updateOrderStateById(orderReq);
    }


}