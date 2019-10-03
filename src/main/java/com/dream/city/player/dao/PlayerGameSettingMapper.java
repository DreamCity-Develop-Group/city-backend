package com.dream.city.player.dao;


import com.dream.city.player.entity.PlayerGameSetting;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PlayerGameSettingMapper {

    int deleteByPrimaryKey(Long id);

    int insertSelective(PlayerGameSetting record);

    PlayerGameSetting selectByPrimaryKey(Long id);

    List<PlayerGameSetting> getGameSettingList(PlayerGameSetting record);

    int updateByPrimaryKeySelective(PlayerGameSetting record);

    int updateByType(PlayerGameSetting record);

    PlayerGameSetting selectByType(@Param("type") String type);

}