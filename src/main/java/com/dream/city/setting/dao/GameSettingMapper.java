package com.dream.city.setting.dao;


import com.dream.city.setting.entity.GameSetting;
import org.springframework.stereotype.Repository;

@Repository
public interface GameSettingMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(GameSetting record);

    GameSetting selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(GameSetting record);

    int updateByType(GameSetting record);

    GameSetting selectByType(String type);

}