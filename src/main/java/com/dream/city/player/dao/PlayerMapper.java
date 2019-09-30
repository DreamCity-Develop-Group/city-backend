package com.dream.city.player.dao;


import com.dream.city.player.entity.Player;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface PlayerMapper {

    Integer deleteByPlayerId(String playerId);

    Integer insertSelective(Player record);

    Player getPlayerById(Player record);

    List<Map> getPlayers(Page pageReq);
    Integer getPlayersCount(Page pageReq);

    /**
     * 广场玩家列表
     * @param pageReq
     * @return
     */
    List<Map> getSquareFriends(Page pageReq);
    Integer getSquareFriendsCount(Page pageReq);

    Integer updateByPlayerId(Player record);

    Player getPlayerByInvite(String invite);

    Player getPlayerByAccount(String account);
}