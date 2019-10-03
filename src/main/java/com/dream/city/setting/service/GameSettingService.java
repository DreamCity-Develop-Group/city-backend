package com.dream.city.setting.service;

import com.dream.city.setting.entity.GameSetting;

import java.util.List;

/**
 * 游戏设置
 */
public interface GameSettingService {

    int deleteGameSettingById(Long id);

    int updateGameSettingById(GameSetting record);

    int insertGameSetting(GameSetting record);

    GameSetting getGameSettingById(Long id);

    List<GameSetting> getGameSettingList(GameSetting record);


}
