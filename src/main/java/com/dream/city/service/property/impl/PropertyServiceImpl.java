package com.dream.city.service.property.impl;


import com.dream.city.base.model.Page;
import com.dream.city.base.model.entity.CityInvest;
import com.dream.city.base.model.mapper.CityInvestMapper;
import com.dream.city.base.model.req.CityInvestReq;
import com.dream.city.base.model.resp.InvestResp;
import com.dream.city.base.model.resp.PropertyResp;
import com.dream.city.base.utils.DataUtils;
import com.dream.city.service.property.PropertyService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
    private CityInvestMapper investMapper;



    @Override
    public int insertInvest(CityInvest record) {
        Integer integer = investMapper.insertSelective(record);
        return integer ==null?0:integer;
    }

    @Override
    public int deletePropertyById(Integer inId) {
        Integer integer = investMapper.deleteByPrimaryKey(inId);
        return integer ==null?0:integer;
    }

    @Override
    public PropertyResp getInvestByIdOrName(Integer inId, String inName) {
        if (inId == null){
            return null;
        }
        CityInvest property = investMapper.selectByPrimaryKey(inId);
        return DataUtils.toJavaObject(property,PropertyResp.class);
    }

    @Override
    public int updateInvest(CityInvest record) {
        Integer integer = investMapper.updateByPrimaryKeySelective(record);
        return integer ==null?0:integer;
    }

    @Override
    public PageInfo<PropertyResp> getInvestLsit(Page pageReq) {
        CityInvestReq invest = DataUtils.toJavaObject(pageReq.getCondition(), CityInvestReq.class);
        PageHelper.startPage(pageReq.getPageNum(),pageReq.getPageSize(),pageReq.isCount());
        List<InvestResp> investLsit = investMapper.getInvestLsit(invest);

        List<PropertyResp> lsit = new ArrayList<>();
        if (!CollectionUtils.isEmpty(investLsit)){
            lsit = new ArrayList<>();
            PropertyResp propertyResp = null;
            for (InvestResp property : investLsit){
                propertyResp = DataUtils.toJavaObject(property,PropertyResp.class);
                propertyResp.setInPersonalTax(property.getPersonalInTax());
                propertyResp.setInEnterpriseTax(property.getEnterpriseIntax());

                lsit.add(propertyResp);
            }
        }
        return new PageInfo<>(lsit);
    }



}
