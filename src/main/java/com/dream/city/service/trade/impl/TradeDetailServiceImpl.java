package com.dream.city.service.trade.impl;

import com.dream.city.base.model.Page;
import com.dream.city.base.model.entity.TradeDetail;
import com.dream.city.base.model.mapper.TradeDetailMapper;
import com.dream.city.base.model.req.PlayerTradeReq;
import com.dream.city.base.model.resp.PlayerTradeResp;
import com.dream.city.base.utils.DataUtils;
import com.dream.city.service.trade.TradeDetailService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TradeDetailServiceImpl implements TradeDetailService {

    @Autowired
    TradeDetailMapper detailMapper;

    @Override
    public TradeDetail getTradeDetailById(Integer id) {
        return detailMapper.selectByPrimaryKey(id);
    }

    @Override
    public PageInfo<PlayerTradeResp> getTradeDetail(Page page) {
        PlayerTradeReq record = DataUtils.toJavaObject(page.getCondition(),PlayerTradeReq.class);
        List<PlayerTradeResp> detailList = detailMapper.getTradeDetailList(record);
        return new PageInfo<>(detailList);
    }


}
