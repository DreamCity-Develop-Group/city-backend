package com.dream.city.service.player;

import com.dream.city.base.model.Page;
import com.dream.city.base.model.entity.PlayerLikesLog;
import com.dream.city.base.model.resp.PlayerLikesResp;
import com.github.pagehelper.PageInfo;

public interface LikesLogService {


    PlayerLikesResp getLikesLogById(Integer id);

    PageInfo<PlayerLikesResp> getLikesLogList(Page page);

    /**
     * 当天点赞次数
     * 好友
     * @param record
     * @return
     */
    int playerLikesCountToday(PlayerLikesLog record);

    /**
     * 当天点赞次数
     * 投资
     * @param record
     * @return
     */
    int investLikesCountToday(PlayerLikesLog record);
}