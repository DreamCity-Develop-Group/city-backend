package com.dream.city.service.trade;

import com.dream.city.base.model.Page;
import com.dream.city.base.model.resp.PlayerTradeResp;
import com.github.pagehelper.PageInfo;


public interface TradeDetailService {


    PlayerTradeResp getTradeDetailById(Integer id);

    PageInfo<PlayerTradeResp> getTradeDetailList(Page record);


}