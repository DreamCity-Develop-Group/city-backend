package com.dream.city.service.trade;

import com.dream.city.base.model.Page;
import com.dream.city.base.model.entity.PlayerTrade;
import com.dream.city.base.model.entity.TradeDetail;
import com.dream.city.base.model.resp.PlayerTradeResp;
import com.github.pagehelper.PageInfo;


public interface TradeDetailService {

    TradeDetail insert(TradeDetail record);

    PlayerTradeResp getTradeDetailById(Integer id);

    PageInfo<PlayerTradeResp> getTradeDetailList(Page record);


}