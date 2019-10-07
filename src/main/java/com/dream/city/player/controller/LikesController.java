package com.dream.city.player.controller;

import com.dream.city.base.model.entity.PlayerLikes;
import com.dream.city.base.model.mapper.PlayerLikesLogMapper;
import com.dream.city.base.model.req.PlayerLikesReq;
import com.dream.city.player.service.LikesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 点赞
 */
@RestController
@RequestMapping("/likes")
public class LikesController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    LikesService likesService;
    @Autowired
    PlayerLikesLogMapper likesLogMapper;



    /**
     * 玩家点赞总数
     * @param playerId
     * @return
     */
    @RequestMapping("/playerLikesCount")
    public int playerLikesCount(String playerId){
        logger.info("获取玩家点赞总数，{}",playerId);
        PlayerLikesReq record = new PlayerLikesReq();
        record.setLikedPlayerId(playerId);
        int i = likesService.playerLikesCount(record);
        return i;
    }

    /**
     * 点赞项目
     * @param like
     * @return
     */
    @RequestMapping("/playerLikesList")
    public List<PlayerLikes> playerLikesList(PlayerLikesReq like){
        logger.info("获取点赞项目，{}",like);
        List<PlayerLikes> likesList = null;
        try {
            likesList = likesService.playerLikesList(like);
        }catch (Exception e){
            logger.error("获取点赞项目异常",e);
        }
        return likesList;
    }






}
