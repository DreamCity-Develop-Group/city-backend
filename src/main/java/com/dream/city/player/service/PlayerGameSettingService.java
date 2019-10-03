package com.dream.city.player.service;

import com.dream.city.base.PageReq;
import com.dream.city.player.dto.PlayerGameSettingReq;
import com.dream.city.player.dto.PlayerGameSettingResp;
import com.dream.city.player.entity.PlayerGameSetting;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * 游戏设置
 */
public interface PlayerGameSettingService {

    int deleteGameSettingById(Long id);

    int updateGameSettingById(PlayerGameSetting record);

    int insertGameSetting(PlayerGameSetting record);

    PlayerGameSettingResp getGameSettingById(Long id);

    PageInfo<PlayerGameSettingResp> getGameSettingList(PageReq<PlayerGameSettingReq> record);


}
