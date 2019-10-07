package com.dream.city.player.service;

import com.dream.city.base.model.Page;
import com.dream.city.base.model.entity.PlayerGameSetting;
import com.dream.city.base.model.req.PlayerGameSettingReq;
import com.dream.city.base.model.resp.PlayerGameSettingResp;
import com.github.pagehelper.PageInfo;

/**
 * 游戏设置
 */
public interface PlayerGameSettingService {

    int deleteGameSettingById(Long id);

    int updateGameSettingById(PlayerGameSetting record);

    int insertGameSetting(PlayerGameSetting record);

    PlayerGameSettingResp getGameSettingById(Long id);

    PageInfo<PlayerGameSettingResp> getGameSettingList(Page record);


}
