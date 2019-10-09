package com.dream.city.service.trade;


import com.dream.city.base.model.Page;
import com.dream.city.base.model.entity.PlayerTrade;
import com.dream.city.base.model.req.PlayerTradeReq;
import com.dream.city.base.model.resp.PlayerTradeResp;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * 交易记录
 */
public interface PlayerTradeService {

    PlayerTrade insertPlayerTrade(PlayerTrade record);

    int updatePlayerTrade(PlayerTrade record);

    PlayerTradeResp getPlayerTradeById(Integer tradeId);

    PlayerTrade getPlayerTrade(PlayerTrade record);

    PageInfo<PlayerTradeResp> getPlayerTradeList(Page page);

}
