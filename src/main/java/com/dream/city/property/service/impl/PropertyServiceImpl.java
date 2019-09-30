package com.dream.city.property.service.impl;


import com.dream.city.property.dao.PropertyMapper;
import com.dream.city.property.entity.Property;
import com.dream.city.property.service.PropertyService;
import com.github.pagehelper.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PropertyServiceImpl implements PropertyService {

    @Autowired
    private PropertyMapper investMapper;



    @Override
    public int insertInvest(Property record) {
        Integer integer = investMapper.insertSelective(record);
        return integer ==null?0:integer;
    }

    @Override
    public Property getInvestByIdOrName(Property record) {
        if (record.getInId() == null && StringUtils.isBlank(record.getInName())){
            return null;
        }
        return investMapper.selectByPrimaryKey(record);
    }

    @Override
    public int updateInvest(Property record) {
        Integer integer = investMapper.updateByPrimaryKeySelective(record);
        return integer ==null?0:integer;
    }

    @Override
    public Page<Property> getInvestLsit(Page<Property> pageReq) {
        Page<Property> page = new Page<>();
        Property record = new Property();

        investMapper.getInvestLsit(record);
        return page;
    }

}
