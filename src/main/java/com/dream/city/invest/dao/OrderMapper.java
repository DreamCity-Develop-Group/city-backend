package com.dream.city.invest.dao;


import com.dream.city.invest.entity.Order;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderMapper {

    Integer deleteByPrimaryKey(Integer orderId);

    Order insertSelective(Order record);

    Order selectByPrimaryKey(Order record);

    Integer updateByPrimaryKeySelective(Order record);

    List<Order> getInvestOrders(Order record);

    int countOrdersByPayerIdInvestId(Order record);

}