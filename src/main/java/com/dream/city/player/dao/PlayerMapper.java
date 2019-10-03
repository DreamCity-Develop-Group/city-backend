package com.dream.city.player.dao;


import com.dream.city.player.entity.Player;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface PlayerMapper {

    Integer deleteByPlayerId(String playerId);

    Integer insertSelective(Player record);

    Player getPlayerById(Player record);

    List<Map> getPlayers(Player record);

    Integer updateByPlayerId(Player record);

    Player getPlayerByInvite(String invite);

}