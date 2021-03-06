package com.dream.city.service.trade.impl;


import com.dream.city.base.model.Page;
import com.dream.city.base.model.entity.PlayerTrade;
import com.dream.city.base.model.mapper.PlayerTradeMapper;
import com.dream.city.base.model.req.PlayerTradeReq;
import com.dream.city.base.model.resp.PlayerTradeResp;
import com.dream.city.base.utils.DataUtils;
import com.dream.city.service.trade.PlayerTradeService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class PlayerTradeServiceImpl implements PlayerTradeService {


    @Autowired
    PlayerTradeMapper tradeMapper;



    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public PlayerTrade insertPlayerTrade(PlayerTrade record) {
        Integer integer = tradeMapper.insertSelective(record);
        if (integer == null || integer < 1){
            return null;
        }
        return record;
    }

    @Override
    @Transactional
    public int updatePlayerTrade(PlayerTrade record) {
        Integer integer = tradeMapper.updateByPrimaryKeySelective(record);
        return integer ==null?0:integer;
    }

    @Override
    public PlayerTradeResp getPlayerTradeById(Integer dynId) {
        if (dynId == null){
            return null;
        }
        return tradeMapper.getPlayerTradeById(dynId);
    }

    @Override
    public PlayerTrade getPlayerTrade(PlayerTrade record) {
        return tradeMapper.getPlayerTrade(record);
    }

    @Override
    public PageInfo<PlayerTradeResp> getPlayerTradeList(Page page) {
        PlayerTradeReq record = DataUtils.toJavaObject(page.getCondition(),PlayerTradeReq.class);
        List<PlayerTradeResp> tradeList = tradeMapper.getPlayerTradeList(record);
        return new PageInfo<>(tradeList);
    }


}
