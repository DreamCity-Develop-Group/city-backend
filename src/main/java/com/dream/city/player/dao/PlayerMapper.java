package com.dream.city.player.dao;


import com.dream.city.player.dto.PlayerResp;
import com.dream.city.player.entity.Player;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PlayerMapper {

    Integer deleteByPlayerId(String playerId);

    Integer insertSelective(Player record);

    PlayerResp getPlayerById(Player record);

    List<PlayerResp> getPlayers(Player record);

    Integer updateByPlayerId(Player record);

    Player getPlayerByInvite(String invite);

}