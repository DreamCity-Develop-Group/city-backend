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
import com.dream.city.verify.entity.TradeVerify;
import com.dream.city.verify.service.TradeVerifyService;
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
    @Autowired
    private TradeVerifyService verifyService;



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

    @Override
    public int updateOrderStateById(Order record) {
        Integer integer = orderMapper.updateOrderStateById(record);
        return integer ==null?0:integer;
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
        String orderState = "";
        switch (resp.getOrderState()){
            case "SUBSCRIBE": orderState = InvestStatus.SUBSCRIBE.getDesc();break;
            case "SUBSCRIBED": orderState = InvestStatus.SUBSCRIBED.getDesc();break;
            case "SUBSCRIBE_PASS": orderState = InvestStatus.SUBSCRIBE_PASS.getDesc();break;
            case "INVEST": orderState = InvestStatus.INVEST.getDesc();break;
            case "MANAGEMENT": orderState = InvestStatus.MANAGEMENT.getDesc();break;
            case "EXTRACT": orderState = InvestStatus.EXTRACT.getDesc();break;
            case "FINISHED": orderState = InvestStatus.FINISHED.getDesc();break;
            case "CANCEL": orderState = InvestStatus.CANCEL.getDesc();break;
            case "SUBSCRIBE_VERIFY_FAIL": orderState = InvestStatus.SUBSCRIBE_VERIFY_FAIL.getDesc();break;
            case "INVALID": orderState = InvestStatus.INVALID.getDesc();break;
            default:;
        }
        resp.setOrderState(orderState);

        TradeVerify record = new TradeVerify();
        record.setVerifyOrderId(order.getOrderId());
        TradeVerify verify = verifyService.getTradeVerify(record);
        if (verify != null){
            resp.setVerifyStatus(verify.getVerifyStatus());
            resp.setVerifyDesc(verify.getVerifyDesc());
        }

        return resp;
    }

}
