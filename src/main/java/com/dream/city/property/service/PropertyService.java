package com.dream.city.property.service;


import com.dream.city.property.entity.Property;

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


    /**
     * 查询物业
     * @param record: inId 、inName
     * @return
     */
    Property getInvestByInName(Property record);

    /**
     * 更新物业
     * @param record
     * @return
     */
    int updateInvest(Property record);

    /**
     * 物业列表
     * @param record
     * @return
     */
    List<Property> getInvestLsit(Property record);





}
