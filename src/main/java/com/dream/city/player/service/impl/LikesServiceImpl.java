package com.dream.city.player.service.impl;

import com.dream.city.player.dao.PlayerLikesLogMapper;
import com.dream.city.player.dao.PlayerLikesMapper;
import com.dream.city.player.entity.PlayerLikes;
import com.dream.city.player.entity.PlayerLikesLog;
import com.dream.city.player.service.LikesService;
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
    public int playerLike(PlayerLikes record) {
        Integer i = 0;
        if (record.getLikedId() == null){
            record.setCreateTime(new Date());
            i = playerLikesMapper.insertSelective(record);
        }else {
            int count = getLikeCount(record.getLikedId());
            record.setUpdateTime(new Date());
            record.setLikedInvestTotal(count + record.getLikedInvestTotal());
            i = playerLikesMapper.updateByPrimaryKeySelective(record);
        }
        if(i>0){
            savePlayerLikesLog(record);
        }
        return i == null? 0: i;
    }


    @Override
    public int cancelLike(PlayerLikes record) {
        int count = getLikeCount(record.getLikedId());
        record.setUpdateTime(new Date());
        record.setLikedInvestTotal(count > 0? (count - 1): count);
        Integer i = playerLikesMapper.updateByPrimaryKeySelective(record);
        if(i>0){
            savePlayerLikesLog(record);
        }
        return i == null? 0: i;
    }

    @Override
    public int playerLikesCount(PlayerLikes record) {
        Integer likesCount = playerLikesMapper.playerLikesCount(record);
        return likesCount == null? 0: likesCount;
    }

    @Override
    public List<PlayerLikes> playerLikesList(PlayerLikes record) {
        return playerLikesMapper.playerLikesList(record);
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
        Integer count =likes.getLikedInvestTotal();
        return count == null?0:count;
    }


    private void savePlayerLikesLog(PlayerLikes playerLikes){
        PlayerLikesLog record = new PlayerLikesLog();
        record.setCreateTime(new Date());
        record.setLikeInvestId(playerLikes.getLikedInvestId());
        record.setLikeLikedId(playerLikes.getLikedPlayerId());
        //record.setLikePlayerId(playerLikes.getLikePlayerId());
        likesLogMapper.insertSelective(record);
    }


}
