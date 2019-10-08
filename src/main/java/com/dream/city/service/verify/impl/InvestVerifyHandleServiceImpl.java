package com.dream.city.service.verify.impl;

import com.dream.city.base.Result;
import com.dream.city.base.model.entity.InvestOrder;
import com.dream.city.base.model.entity.TradeVerify;
import com.dream.city.base.model.enu.InvestStatus;
import com.dream.city.base.model.enu.VerifyType;
import com.dream.city.base.model.req.VerifyReq;
import com.dream.city.base.model.resp.InvestOrderResp;
import com.dream.city.service.invest.OrderService;
import com.dream.city.service.verify.InvestVerifyHandleService;
import com.dream.city.service.verify.TradeVerifyService;
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
        InvestOrderResp order = orderService.getInvestOrderById(verifyReq.getOrderId());
        //审核将状态改成投资
        int i = 0;
        if (order == null){
            descr = "没找到预约记录";
        }else {
            if (InvestStatus.SUBSCRIBE.name().equalsIgnoreCase(order.getOrderState())){
                i = this.updateOrderState(verifyReq.getOrderId(), InvestStatus.INVEST.name());
                if (i > 0){
                    success = Boolean.TRUE;
                    descr = "预约审核成功";
                    //生成审核记录
                    this.insertTradeVerify(verifyReq);
                }
            }else {
                descr = "预约投资状态不对，当前状态是：" + order.getOrderState();
            }
        }
        return new Result(success,descr,i);
    }

    @Override
    public Result investOrderVerify(VerifyReq verifyReq) {
        boolean success = Boolean.FALSE;
        String descr = "投资审核失败";
        InvestOrderResp order = orderService.getInvestOrderById(verifyReq.getOrderId());
        int i = 0;
        if (order == null){
            descr = "没找到预约记录";
        }else {
            if (InvestStatus.INVESTED.name().equalsIgnoreCase(order.getOrderState())){
                //审核成功，将状态改成经营中
                i = this.updateOrderState(verifyReq.getOrderId(), InvestStatus.MANAGEMENT.name());
                if (i > 0){
                    success = Boolean.TRUE;
                    descr = "预约审核成功";
                    //生成审核记录
                    this.insertTradeVerify(verifyReq);
                }
            }else {
                descr = "预约投资状态不对，当前状态是：" + order.getOrderState();
            }
        }
        if (i > 0){
            success = Boolean.TRUE;
            descr = "投资审核成功";
            //生成审核记录
            this.insertTradeVerify(verifyReq);

            String tradeType = VerifyType.INVEST.name();
            //玩家账户扣除usdt投资金额


            //玩家账户扣除usdt投资金额流水



            //平台usdt账户增加投资金额


            //平台usdt账户增加投资金额流水

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
        InvestOrder orderReq = new InvestOrder();
        orderReq.setOrderId(orderId);
        orderReq.setOrderState(orderState);
        return orderService.updateOrderStateById(orderReq);
    }


}
