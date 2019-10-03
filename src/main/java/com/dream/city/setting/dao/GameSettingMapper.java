package com.dream.city.setting.dao;


import com.dream.city.setting.entity.GameSetting;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface GameSettingMapper {

    int deleteByPrimaryKey(Long id);

    int insertSelective(GameSetting record);

    GameSetting selectByPrimaryKey(Long id);

    List<GameSetting> getGameSettingList(GameSetting record);

    int updateByPrimaryKeySelective(GameSetting record);

    int updateByType(GameSetting record);

    GameSetting selectByType(@Param("type") String type);

}