package com.dream.city.invest.service.impl;

import com.dream.city.invest.dao.OrderMapper;
import com.dream.city.invest.entity.Order;
import com.dream.city.invest.enu.OrderState;
import com.dream.city.invest.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {


    @Autowired
    private OrderMapper orderMapper;



    @Override
    @Transactional
    public Order insertInvestOrder(Order record) {
        return orderMapper.insertSelective(record);
    }

    @Override
    @Transactional
    public int investOrderInvalid(Order record) {
        record.setOrderState(OrderState.INVALID.name());
        Integer integer = orderMapper.updateByPrimaryKeySelective(record);
        return integer ==null?0:integer;
    }

    @Override
    @Transactional
    public int investOrderCancel(Order record) {
        record.setOrderState(OrderState.CANCEL.name());
        Integer integer = orderMapper.updateByPrimaryKeySelective(record);
        return integer ==null?0:integer;
    }

    @Override
    public Order getInvestOrderById(Order record) {
        if (record.getOrderId() == null){
            return null;
        }
        return orderMapper.selectByPrimaryKey(record);
    }

    @Override
    public List<Order> getInvestOrderList(Order record) {
        return orderMapper.getInvestOrders(record);
    }

    @Override
    public int countOrdersByPayerIdInvestId(Order record) {
        return orderMapper.countOrdersByPayerIdInvestId(record);
    }


}
