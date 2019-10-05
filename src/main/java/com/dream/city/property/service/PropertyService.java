package com.dream.city.property.service;


import com.dream.city.base.PageReq;
import com.dream.city.property.dto.PropertyReq;
import com.dream.city.property.dto.PropertyResp;
import com.dream.city.property.entity.Property;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * 物业/投资项
 */
public interface PropertyService {

    /**
     * 新建物业
     * @param record
     * @return
     */
    int insertInvest(Property record);


    int deletePropertyById(Integer inId);


    /**
     * 查询物业
     * @param  inId 、inName
     * @return
     */
    PropertyResp getInvestByIdOrName(Integer inId,String inName);

    /**
     * 更新物业
     * @param record
     * @return
     */
    int updateInvest(Property record);

    /**
     * 物业列表
     * @param pageReq
     * @return
     */
    PageInfo<PropertyResp> getInvestLsit(PageReq<PropertyReq> pageReq);





}
