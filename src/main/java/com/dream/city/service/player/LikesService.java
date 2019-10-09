package com.dream.city.service.player;


import com.dream.city.base.model.Page;
import com.dream.city.base.model.entity.PlayerLikes;
import com.dream.city.base.model.req.PlayerLikesReq;
import com.dream.city.base.model.resp.PlayerLikesResp;
import com.github.pagehelper.PageInfo;


public interface LikesService {


    /**
     * 点赞
     * @param record
     * @return
     */
    int playerLike(PlayerLikesReq record);

    /**
     * 取消点赞
     * @param record
     * @return
     */
    int cancelLike(PlayerLikesReq record);

    /**
     * 玩家点赞总数
     * @param record
     * @return
     */
    int playerLikesCount(PlayerLikesReq record);

    /**
     * 点赞项目列表
     * @param record
     * @return
     */
    PageInfo<PlayerLikesResp> playerLikesList(Page record);


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