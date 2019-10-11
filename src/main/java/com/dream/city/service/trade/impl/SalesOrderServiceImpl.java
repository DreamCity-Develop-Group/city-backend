package com.dream.city.service.trade.impl;

import com.dream.city.base.model.Page;
import com.dream.city.base.model.entity.SalesOrder;
import com.dream.city.base.model.mapper.SalesOrderMapper;
import com.dream.city.base.utils.DataUtils;
import com.dream.city.service.trade.SalesOrderService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalesOrderServiceImpl implements SalesOrderService {


    @Autowired
    SalesOrderMapper salesOrderMapper;



    @Override
    public PageInfo<SalesOrder> getSalesOrderList(Page page) {
        SalesOrder record = DataUtils.toJavaObject(page.getCondition(),SalesOrder.class);
        PageHelper.startPage(page.getPageNum(),page.getPageSize());
        List<SalesOrder> orderList = salesOrderMapper.getSalesOrderList(record);
        return new PageInfo<>(orderList);
    }

    @Override
    public SalesOrder getSalesOrderById(Integer id) {
        return salesOrderMapper.getSalesOrderById(id);
    }











}
