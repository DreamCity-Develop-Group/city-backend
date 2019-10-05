package com.dream.city.invest.service.impl;

import com.dream.city.base.PageReq;
import com.dream.city.invest.dao.OrderMapper;
import com.dream.city.invest.dto.OrderReq;
import com.dream.city.invest.dto.OrderResp;
import com.dream.city.invest.entity.Order;
import com.dream.city.invest.enu.InvestStatus;
import com.dream.city.invest.service.OrderService;
import com.dream.city.player.entity.Player;
import com.dream.city.player.service.PlayerService;
import com.dream.city.property.dto.PropertyResp;
import com.dream.city.property.service.PropertyService;
import com.dream.city.util.DataUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {


    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private PropertyService propertyService;
    @Autowired
    private PlayerService playerService;



    @Override
    @Transactional
    public Order insertInvestOrder(Order record) {
        return orderMapper.insertSelective(record);
    }

    @Override
    @Transactional
    public int investOrderInvalid(Order record) {
        record.setOrderState(InvestStatus.INVALID.name());
        Integer integer = orderMapper.updateByPrimaryKeySelective(record);
        return integer ==null?0:integer;
    }

    @Override
    @Transactional
    public int investOrderCancel(Order record) {
        record.setOrderState(InvestStatus.CANCEL.name());
        Integer integer = orderMapper.updateByPrimaryKeySelective(record);
        return integer ==null?0:integer;
    }

    @Override
    public OrderResp getInvestOrderById(Integer orderId) {
        if (orderId == null){
            return null;
        }
        Order order = orderMapper.selectByPrimaryKey(orderId);
        return this.getOrderResp(order);
    }

    @Override
    public PageInfo<OrderResp> getInvestOrderList(PageReq<OrderReq> record) {
        PropertyResp property = propertyService.getInvestByIdOrName(null, record.getCondition().getInName());
        Player player = playerService.getPlayerByName(record.getCondition().getPayerName());
        Order recordReq =  DataUtils.toJavaObject(record.getCondition(),Order.class);
        if (property != null) {
            recordReq.setOrderInvestId(property.getInId());
        }
        if (player != null) {
            recordReq.setOrderPayerId(player.getPlayerId());
        }
        PageHelper.startPage(record.getPageNum(),record.getPageSize(),record.isCount());
        List<Order> orders = orderMapper.getInvestOrders(recordReq);
        List<OrderResp> ordersResp = new ArrayList<>();
        if (!CollectionUtils.isEmpty(orders)){
            for (Order order : orders){
                ordersResp.add(this.getOrderResp(order));
            }
        }
        return new PageInfo<>(ordersResp);
    }

    @Override
    public int countOrdersByPayerIdPropertyId(Order record) {
        return orderMapper.countOrdersByPayerIdPropertyId(record);
    }

    private OrderResp getOrderResp(Order order){
        OrderResp resp = DataUtils.toJavaObject(order,OrderResp.class);
        Player player = playerService.getPlayerByPlayerId(order.getOrderPayerId());
        if (player != null){
            resp.setPayerName(player.getPlayerName());
        }
        PropertyResp propertyResp = propertyService.getInvestByIdOrName(order.getOrderInvestId(), null);
        if (propertyResp != null){
            resp.setInName(propertyResp.getInName());
            resp.setInTax(BigDecimal.valueOf(Double.parseDouble(String.valueOf(propertyResp.getInTax()))));
        }
        return resp;
    }

}
