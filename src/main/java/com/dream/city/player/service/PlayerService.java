package com.dream.city.player.service;

import com.dream.city.player.entity.Player;
import com.dream.city.player.entity.PlayerGrade;
import com.github.pagehelper.Page;
import org.springframework.stereotype.Repository;


/**
 * @author Wvv
 */
@Repository
public interface PlayerService {


    Player update(Player player);

    Player getPlayer(Player player);

    Player getPlayerById(String playerId);

    /**
     * 玩家列表
     * @param pageReq
     * @return
     */
    Page getPlayers(Page pageReq);

    Player getPlayerByName(String username, String playerNick);

    Player getPlayerByInvite(String invite);

    Player getPlayerByAccount(String account);

    /**
     * 获取玩家等级
     * @param playerId
     * @return
     */
    PlayerGrade getPlayerGradeByPlayerId(String playerId);



}
