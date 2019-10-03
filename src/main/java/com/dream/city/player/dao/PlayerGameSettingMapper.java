package com.dream.city.player.dao;


import com.dream.city.player.entity.PlayerGameSetting;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PlayerGameSettingMapper {

    Integer deleteByPrimaryKey(Long id);

    Integer insertSelective(PlayerGameSetting record);

    PlayerGameSetting selectByPrimaryKey(Long id);

    List<PlayerGameSetting> getGameSettingList(PlayerGameSetting record);

    Integer updateByPrimaryKeySelective(PlayerGameSetting record);

    Integer updateByType(PlayerGameSetting record);

    PlayerGameSetting selectByType(@Param("type") String type);

}