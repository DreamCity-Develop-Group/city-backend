package com.dream.city.service.trade.impl;

import com.dream.city.base.model.Page;
import com.dream.city.base.model.entity.PlayerTrade;
import com.dream.city.base.model.entity.TradeDetail;
import com.dream.city.base.model.mapper.TradeDetailMapper;
import com.dream.city.base.model.req.PlayerTradeReq;
import com.dream.city.base.model.resp.PlayerTradeResp;
import com.dream.city.base.utils.DataUtils;
import com.dream.city.service.trade.TradeDetailService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class TradeDetailServiceImpl implements TradeDetailService {

    @Autowired
    TradeDetailMapper detailMapper;

    @Override
    @Transactional
    public TradeDetail insert(TradeDetail record) {
        TradeDetail result = detailMapper.insertSelective(record);
        return result;
    }

    @Override
    public PlayerTradeResp getTradeDetailById(Integer id) {
        PlayerTradeReq record = new PlayerTradeReq();
        record.setDetailId(id);
        List<PlayerTradeResp> detailList = detailMapper.getTradeDetailList(record);
        if (CollectionUtils.isEmpty(detailList)){
            return null;
        }
        return detailList.get(0);
    }

    @Override
    public PageInfo<PlayerTradeResp> getTradeDetailList(Page page) {
        PlayerTradeReq record = DataUtils.toJavaObject(page.getCondition(),PlayerTradeReq.class);
        List<PlayerTradeResp> detailList = detailMapper.getTradeDetailList(record);
        return new PageInfo<>(detailList);
    }


}
