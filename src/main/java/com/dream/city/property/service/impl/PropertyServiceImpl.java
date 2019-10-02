package com.dream.city.property.service.impl;


import com.dream.city.base.PageReq;
import com.dream.city.property.dao.PropertyMapper;
import com.dream.city.property.entity.Property;
import com.dream.city.property.service.PropertyService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


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
    public PageInfo<Property> getInvestLsit(PageReq<Property> pageReq) {
        PageHelper.startPage(pageReq.getPageNum(),pageReq.getPageSize(),pageReq.isCount());
        Property record = pageReq.getCondition();
        return new PageInfo<>(investMapper.getInvestLsit(record));
    }

}
