package com.dream.city.property.service.impl;


import com.dream.city.base.PageReq;
import com.dream.city.property.dao.PropertyMapper;
import com.dream.city.property.dto.PropertyReq;
import com.dream.city.property.dto.PropertyResp;
import com.dream.city.property.entity.Property;
import com.dream.city.property.service.PropertyService;
import com.dream.city.util.DataUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * @author
 */
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
    public int deletePropertyById(Integer inId) {
        Integer integer = investMapper.deleteByPrimaryKey(inId);
        return integer ==null?0:integer;
    }

    @Override
    public PropertyResp getInvestByIdOrName(Integer inId,String inName) {
        if (inId == null && StringUtils.isBlank(inName)){
            return null;
        }
        Property record = new Property();
        record.setInId(inId);
        record.setInName(inName);
        Property property = investMapper.selectByPrimaryKey(record);
        return DataUtils.toJavaObject(property,PropertyResp.class);
    }

    @Override
    public int updateInvest(Property record) {
        Integer integer = investMapper.updateByPrimaryKeySelective(record);
        return integer ==null?0:integer;
    }

    @Override
    public PageInfo<PropertyResp> getInvestLsit(PageReq<PropertyReq> pageReq) {
        PageHelper.startPage(pageReq.getPageNum(),pageReq.getPageSize(),pageReq.isCount());
        List<Property> investLsit = investMapper.getInvestLsit(pageReq.getCondition());

        List<PropertyResp> lsit = null;
        if (!CollectionUtils.isEmpty(investLsit)){
            lsit = new ArrayList<>();
            PropertyResp propertyResp = null;
            for (Property property : investLsit){
                propertyResp = DataUtils.toJavaObject(property,PropertyResp.class);
                lsit.add(propertyResp);
            }
        }
        return new PageInfo<>(lsit);
    }



}
