package com.dream.city.trade.dao;


import com.dream.city.trade.entity.PlayerTrade;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 交易流水
 */
@Mapper
public interface PlayerTradeMapper {

    Integer insertSelective(PlayerTrade record);

    PlayerTrade getPlayerTradeById(Integer tradeId);

    PlayerTrade getPlayerTrade(PlayerTrade record);

    List<PlayerTrade> getPlayerTradeList(PlayerTrade record);

    Integer updateByPrimaryKeySelective(PlayerTrade record);

}