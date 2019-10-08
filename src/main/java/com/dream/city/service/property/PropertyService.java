package com.dream.city.service.property;


import com.dream.city.base.model.Page;
import com.dream.city.base.model.entity.CityInvest;
import com.dream.city.base.model.resp.PropertyResp;
import com.github.pagehelper.PageInfo;

/**
 * 物业/投资项
 */
public interface PropertyService {

    /**
     * 新建物业
     * @param record
     * @return
     */
    int insertInvest(CityInvest record);


    int deletePropertyById(Integer inId);


    /**
     * 查询物业
     * @param  inId 、inName
     * @return
     */
    PropertyResp getInvestByIdOrName(Integer inId, String inName);

    /**
     * 更新物业
     * @param record
     * @return
     */
    int updateInvest(CityInvest record);

    /**
     * 物业列表
     * @param pageReq
     * @return
     */
    PageInfo<PropertyResp> getInvestLsit(Page pageReq);





}
