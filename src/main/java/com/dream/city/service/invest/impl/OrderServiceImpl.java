package com.dream.city.service.invest.impl;

import com.dream.city.base.model.Page;
import com.dream.city.base.model.entity.InvestOrder;
import com.dream.city.base.model.enu.InvestStatus;
import com.dream.city.base.model.mapper.InvestOrderMapper;
import com.dream.city.base.model.req.InvestOrderReq;
import com.dream.city.base.model.resp.InvestOrderResp;
import com.dream.city.base.model.resp.PlayerResp;
import com.dream.city.base.model.resp.PropertyResp;
import com.dream.city.base.utils.DataUtils;
import com.dream.city.service.invest.OrderService;
import com.dream.city.service.player.PlayerService;
import com.dream.city.service.property.PropertyService;
import com.dream.city.service.verify.TradeVerifyService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {


    @Autowired
    private InvestOrderMapper orderMapper;

    @Autowired
    private PropertyService propertyService;
    @Autowired
    private PlayerService playerService;
    @Autowired
    private TradeVerifyService verifyService;



    @Override
    @Transactional
    public InvestOrder insertInvestOrder(InvestOrder record) {
        Integer integer = orderMapper.insertSelective(record);
        return (integer == null || integer < 1)? null:record;
    }

    @Override
    @Transactional
    public int investOrderInvalid(InvestOrder record) {
        record.setOrderState(InvestStatus.INVALID.getStatus());
        Integer integer = orderMapper.updateByPrimaryKeySelective(record);
        return integer ==null?0:integer;
    }

    @Override
    @Transactional
    public int investOrderCancel(InvestOrder record) {
        record.setOrderState(InvestStatus.CANCEL.getStatus());
        Integer integer = orderMapper.updateByPrimaryKeySelective(record);
        return integer ==null?0:integer;
    }

    @Override
    public InvestOrderResp getInvestOrderById(Integer orderId) {
        if (orderId == null){
            return null;
        }
        InvestOrderResp order = orderMapper.selectByPrimaryKey(orderId);
        //return this.getOrderResp(order);
        return (order);
    }

    @Override
    public PageInfo<InvestOrderResp> getInvestOrderList(Page record) {
        InvestOrderReq invest = DataUtils.toJavaObject(record.getCondition(),InvestOrderReq.class);
        PropertyResp property = null;
        PlayerResp player = null;
        if (invest != null){
            property = propertyService.getInvestByIdOrName(null, invest.getInName());
            player = playerService.getPlayerByName(invest.getPayerName());
        }
        InvestOrderReq recordReq =  DataUtils.toJavaObject(record.getCondition(),InvestOrderReq.class);
        if (property != null) {
            recordReq.setInvestId(property.getInId());
        }
        if (player != null) {
            recordReq.setPlayerId(player.getPlayerId());
        }
        PageHelper.startPage(record.getPageNum(),record.getPageSize(),record.isCount());
        List<InvestOrderResp> orders = orderMapper.getInvestOrders(recordReq);

        List<InvestOrderResp> ordersResp = new ArrayList<>();
        if (!CollectionUtils.isEmpty(orders)){
            for (InvestOrderResp order : orders){
                ordersResp.add(order);//this.getOrderResp(order)
            }
        }
        return new PageInfo<>(ordersResp);
    }

    @Override
    public int countOrdersByPayerIdPropertyId(InvestOrder record) {
        return orderMapper.countOrdersByPayerIdInvestId(record);
    }

    @Override
    @Transactional
    public int updateOrderStateById(InvestOrder record) {
        Integer integer = orderMapper.updateOrderStateById(record);
        return integer ==null?0:integer;
    }

    private InvestOrderResp getOrderResp(InvestOrderResp resp){
        String orderState = "";
        switch (resp.getOrderState()){
            case "SUBSCRIBE": orderState = InvestStatus.SUBSCRIBE.getDesc();break;
            case "SUBSCRIBED": orderState = InvestStatus.SUBSCRIBED.getDesc();break;
            case "SUBSCRIBE_PASS": orderState = InvestStatus.SUBSCRIBE_PASS.getDesc();break;
            case "INVEST": orderState = InvestStatus.INVEST.getDesc();break;
            case "INVESTED": orderState = InvestStatus.INVESTED.getDesc();break;
            case "MANAGEMENT": orderState = InvestStatus.MANAGEMENT.getDesc();break;
            case "EXTRACT": orderState = InvestStatus.EXTRACT.getDesc();break;
            case "FINISHED": orderState = InvestStatus.FINISHED.getDesc();break;
            case "CANCEL": orderState = InvestStatus.CANCEL.getDesc();break;
            case "SUBSCRIBE_VERIFY_FAIL": orderState = InvestStatus.SUBSCRIBE_VERIFY_FAIL.getDesc();break;
            case "INVALID": orderState = InvestStatus.INVALID.getDesc();break;
            default:;
        }
        resp.setOrderState(orderState);
        return resp;
    }

}
