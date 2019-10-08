package com.dream.city.service.trade;


import com.dream.city.base.model.entity.PlayerTrade;

import java.util.List;

/**
 * 交易记录
 */
public interface PlayerTradeService {

    PlayerTrade insertPlayerTrade(PlayerTrade record);

    int updatePlayerTrade(PlayerTrade record);

    PlayerTrade getPlayerTradeById(Integer tradeId);

    PlayerTrade getPlayerTrade(PlayerTrade record);

    List<PlayerTrade> getPlayerTradeList(PlayerTrade record);

}
