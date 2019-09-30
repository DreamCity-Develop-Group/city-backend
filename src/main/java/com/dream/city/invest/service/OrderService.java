package com.dream.city.invest.service;

import com.dream.city.invest.entity.Order;

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
     * @param record
     * @return
     */
    Order getInvestOrderById(Order record);

    /**
     * 投资订单列表
     * @param record
     * @return
     */
    List<Order> getInvestOrderList(Order record);

    /**
     * 投资数量
     * @param record orderInvestId、orderPayerId、orderState
     * @return
     */
    int countOrdersByPayerIdInvestId(Order record);
}
