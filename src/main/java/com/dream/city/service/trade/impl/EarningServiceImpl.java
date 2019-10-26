package com.dream.city.service.trade.impl;

import com.dream.city.base.model.Page;
import com.dream.city.base.model.entity.PlayerEarning;
import com.dream.city.base.model.mapper.PlayerEarningMapper;
import com.dream.city.base.model.req.EarningReq;
import com.dream.city.base.model.resp.PlayerEarningResp;
import com.dream.city.service.trade.EarningService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EarningServiceImpl implements EarningService {

    @Autowired
    private PlayerEarningMapper earningMapper;


    @Override
    public PlayerEarning getEarningById(Integer earnId) {
        return earningMapper.selectByPrimaryKey(earnId);
    }

    @Override
    public PlayerEarningResp getEarning(PlayerEarning record) {
        return earningMapper.getPlayerEarning(record);
    }

    @Override
    public PageInfo<PlayerEarningResp> getEarningList(Page page, EarningReq record) {
        PageHelper.startPage(page.getPageNum(),page.getPageSize());
        List<PlayerEarningResp> earningList = earningMapper.getEarningList(record);
        return new PageInfo<>(earningList);
    }

    @Override
    public int updateEarningById(PlayerEarning record) {
        Integer i = earningMapper.updateByPrimaryKeySelective(record);
        return i == null? 0: i;
    }
}
