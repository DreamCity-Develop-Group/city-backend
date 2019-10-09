package com.dream.city.service.player.impl;

import com.dream.city.base.model.Page;
import com.dream.city.base.model.entity.PlayerLikes;
import com.dream.city.base.model.entity.PlayerLikesLog;
import com.dream.city.base.model.mapper.PlayerLikesLogMapper;
import com.dream.city.base.model.mapper.PlayerLikesMapper;
import com.dream.city.base.model.req.PlayerLikesReq;
import com.dream.city.base.model.resp.PlayerLikesResp;
import com.dream.city.base.utils.DataUtils;
import com.dream.city.service.player.LikesService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class LikesServiceImpl implements LikesService {


    @Autowired
    PlayerLikesMapper playerLikesMapper;
    @Autowired
    PlayerLikesLogMapper likesLogMapper;


    @Override
    public int playerLike(PlayerLikesReq record) {
        Integer i = 0;
        if (record.getLikedId() == null){
            record.setCreateTime(new Date());
            i = playerLikesMapper.insertSelective(record);
        }else {
            int count = getLikeCount(record.getLikedId());
            record.setUpdateTime(new Date());
            record.setLikedSetTotal(count + record.getLikedSetTotal());
            i = playerLikesMapper.updateByPrimaryKeySelective(record);
        }
        if(i>0){
            savePlayerLikesLog(record);
        }
        return i == null? 0: i;
    }


    @Override
    public int cancelLike(PlayerLikesReq record) {
        int count = getLikeCount(record.getLikedId());
        record.setUpdateTime(new Date());
        record.setLikedSetTotal(count > 0? (count - 1): count);
        Integer i = playerLikesMapper.updateByPrimaryKeySelective(record);
        if(i>0){
            savePlayerLikesLog(record);
        }
        return i == null? 0: i;
    }

    @Override
    public int playerLikesCount(PlayerLikesReq record) {
        Integer likesCount = playerLikesMapper.playerLikesCount(record);
        return likesCount == null? 0: likesCount;
    }

    @Override
    public PageInfo<PlayerLikesResp> playerLikesList(Page record) {
        PlayerLikesReq likesReq = DataUtils.toJavaObject(record.getCondition(),PlayerLikesReq.class);
        PageHelper.startPage(record.getPageNum(),record.getPageSize(),record.isCount());
        List<PlayerLikesResp> likesList = playerLikesMapper.playerLikesList(likesReq);
        return new PageInfo<>(likesList);
    }

    @Override
    public int playerLikesCountToday(PlayerLikes record) {
        PlayerLikesLog likesLog = new PlayerLikesLog();
        likesLog.setLikeLikedId(record.getLikedPlayerId());
        //likesLog.setLikePlayerId(record.getLikePlayerId());
        return likesLogMapper.playerLikesCountToday(likesLog);
    }

    @Override
    public int investLikesCountToday(PlayerLikes record) {
        PlayerLikesLog likesLog = new PlayerLikesLog();
        likesLog.setLikeLikedId(record.getLikedPlayerId());
        likesLog.setLikeInvestId(record.getLikedInvestId());
        //likesLog.setLikePlayerId(record.getLikePlayerId());
        return likesLogMapper.playerLikesCountToday(likesLog);
    }


    private int getLikeCount(Integer likedId){
        PlayerLikes likes = playerLikesMapper.selectByPrimaryKey(likedId);
        Integer count =likes.getLikedGetTotal();
        return count == null?0:count;
    }


    private void savePlayerLikesLog(PlayerLikesReq playerLikes){
        PlayerLikesLog record = new PlayerLikesLog();
        record.setCreateTime(new Date());
        record.setLikeInvestId(playerLikes.getLikedInvestId());
        record.setLikeLikedId(playerLikes.getLikedPlayerId());
        record.setLikePlayerId(playerLikes.getLikePlayerId());
        likesLogMapper.insertSelective(record);
    }


}
