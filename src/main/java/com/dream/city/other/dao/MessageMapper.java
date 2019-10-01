package com.dream.city.other.dao;


import com.dream.city.other.entity.CityMessage;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 通知
 */
@Mapper
public interface MessageMapper {

    Integer deleteByPrimaryKey(Long id);

    Integer insertSelective(CityMessage record);

    CityMessage selectByPrimaryKey(Long id);

    List<CityMessage> getCityMessageList(CityMessage record);

    Integer updateByPrimaryKeySelective(CityMessage record);

}