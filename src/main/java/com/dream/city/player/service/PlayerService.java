package com.dream.city.player.service;

import com.dream.city.base.PageReq;
import com.dream.city.player.entity.Player;
import com.dream.city.player.entity.PlayerGrade;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Repository;


/**
 * @author Wvv
 */
@Repository
public interface PlayerService {


    Player getPlayer(Player player);

    Player getPlayerByPlayerId(String playerId);

    /**
     * 玩家列表
     * @param pageReq
     * @return
     */
    PageInfo getPlayers(PageReq pageReq);

    Player getPlayerByName(String playerName);

    Player getPlayerByNick(String playerNick);

    Player getPlayerByNameOrNick(String nameOrNick);

    Player getPlayerByInvite(String invite);

    String getPlayerNickByPlayerId(String playerId);


    /**
     * 获取玩家等级
     * @param playerId
     * @return
     */
    PlayerGrade getPlayerGradeByPlayerId(String playerId);


}
