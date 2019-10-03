package com.dream.city.setting.service.impl;

import com.dream.city.setting.dao.GameSettingMapper;
import com.dream.city.setting.entity.GameSetting;
import com.dream.city.setting.service.GameSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author
 */
@Service
public class GameSettingServiceImpl implements GameSettingService {

    @Autowired
    private GameSettingMapper gameSettingMapper;


    @Override
    public int deleteGameSettingById(Long id) {
        return gameSettingMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int updateGameSettingById(GameSetting record) {
        return gameSettingMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int insertGameSetting(GameSetting record) {
        return gameSettingMapper.insertSelective(record);
    }

    @Override
    public GameSetting getGameSettingById(Long id) {
        return gameSettingMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<GameSetting> getGameSettingList(GameSetting record) {
        return gameSettingMapper.getGameSettingList(record);
    }
}
