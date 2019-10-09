package com.dream.city.service.player.impl;

import com.dream.city.base.model.Page;
import com.dream.city.base.model.entity.PlayerLikesLog;
import com.dream.city.base.model.mapper.PlayerLikesLogMapper;
import com.dream.city.base.model.req.PlayerLikesReq;
import com.dream.city.base.model.resp.PlayerLikesResp;
import com.dream.city.base.utils.DataUtils;
import com.dream.city.service.player.LikesLogService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LikesLogServiceImpl implements LikesLogService {


    @Autowired
    PlayerLikesLogMapper likesLogMapper;


    @Override
    public PlayerLikesResp getLikesLogById(Integer id) {
        return likesLogMapper.selectByPrimaryKey(id);
    }

    @Override
    public PageInfo<PlayerLikesResp> getLikesLogList(Page page) {
        PlayerLikesReq record = DataUtils.toJavaObject(page.getCondition(),PlayerLikesReq.class);
        PageHelper.startPage(page.getPageNum(),page.getPageSize());
        List<PlayerLikesResp> likesLogList = likesLogMapper.getLikesLogList(record);
        return new PageInfo<>(likesLogList);
    }

    @Override
    public int playerLikesCountToday(PlayerLikesLog record) {
        return likesLogMapper.playerLikesCountToday(record);
    }

    @Override
    public int investLikesCountToday(PlayerLikesLog record) {
        return likesLogMapper.investLikesCountToday(record);
    }
}
