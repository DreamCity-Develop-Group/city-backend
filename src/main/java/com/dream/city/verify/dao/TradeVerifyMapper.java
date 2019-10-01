package com.dream.city.verify.dao;


import com.dream.city.verify.entity.TradeVerify;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TradeVerifyMapper {

    Integer insertSelective(TradeVerify record);

    Integer updateByPrimaryKeySelective(TradeVerify record);

    TradeVerify getTradeVerifyBiId(Integer verifyId);

    List<TradeVerify> getTradeVerifyList(TradeVerify record);

}