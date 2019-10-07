package com.dream.city.player.service;



import com.dream.city.player.entity.PlayerLikes;

import java.util.List;

public interface LikesService {


    /**
     * 点赞
     * @param record
     * @return
     */
    int playerLike(PlayerLikes record);

    /**
     * 取消点赞
     * @param record
     * @return
     */
    int cancelLike(PlayerLikes record);

    /**
     * 玩家点赞总数
     * @param record
     * @return
     */
    int playerLikesCount(PlayerLikes record);

    /**
     * 点赞项目列表
     * @param record
     * @return
     */
    List<PlayerLikes> playerLikesList(PlayerLikes record);


    /**
     * 当天点赞次数
     * 好友
     * @param record
     * @return
     */
    int playerLikesCountToday(PlayerLikes record);

    /**
     * 当天点赞次数
     * 投资
     * @param record
     * @return
     */
    int investLikesCountToday(PlayerLikes record);

}