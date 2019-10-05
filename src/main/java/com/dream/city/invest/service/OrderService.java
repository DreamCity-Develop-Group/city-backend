package com.dream.city.invest.service;

import com.dream.city.base.PageReq;
import com.dream.city.invest.dto.OrderReq;
import com.dream.city.invest.dto.OrderResp;
import com.dream.city.invest.entity.Order;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * 投资订单
 */
public interface OrderService {



    /**
     * 投资物业
     * @param record
     * @return
     */
    Order insertInvestOrder(Order record);

    /**
     * 投资订单作废
     * @param record
     * @return
     */
    int investOrderInvalid(Order record);

    /**
     * 投资订单取消
     * @param record
     * @return
     */
    int investOrderCancel(Order record);

    /**
     * 查询投资订单
     * @param orderId
     * @return
     */
    OrderResp getInvestOrderById(Integer orderId);

    /**
     * 投资订单列表
     * @param record
     * @return
     */
    PageInfo<OrderResp> getInvestOrderList(PageReq<OrderReq> record);

    /**
     * 投资数量
     * @param record orderInvestId、orderPayerId、orderState
     * @return
     */
    int countOrdersByPayerIdPropertyId(Order record);
}
