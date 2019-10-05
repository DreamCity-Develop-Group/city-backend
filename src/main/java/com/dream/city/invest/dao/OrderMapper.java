package com.dream.city.invest.dao;


import com.dream.city.invest.entity.Order;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderMapper {

    Integer deleteByPrimaryKey(Integer orderId);

    Order insertSelective(Order record);

    Order selectByPrimaryKey(Integer orderId);

    Integer updateByPrimaryKeySelective(Order record);

    Integer updateOrderStateById(Order record);

    List<Order> getInvestOrders(Order record);

    int countOrdersByPayerIdPropertyId(Order record);

}