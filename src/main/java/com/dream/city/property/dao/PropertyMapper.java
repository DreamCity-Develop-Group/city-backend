package com.dream.city.property.dao;


import com.dream.city.property.entity.Property;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface PropertyMapper {


    Integer deleteByPrimaryKey(Integer inId);

    Integer insertSelective(Property record);

    Property selectByPrimaryKey(Property record);

    List<Property> getInvestLsit(Property record);

    Integer updateByPrimaryKeySelective(Property record);


}