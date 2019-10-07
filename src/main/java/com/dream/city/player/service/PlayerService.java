package com.dream.city.player.service;

import com.dream.city.base.PageReq;
import com.dream.city.player.dto.PlayerReq;
import com.dream.city.player.dto.PlayerResp;
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


    PlayerResp getPlayer(Player player);

    PlayerResp getPlayerByrId(Long id);

    PlayerResp getPlayerByPlayerId(String playerId);

    /**
     * 玩家列表
     * @param pageReq
     * @return
     */
    PageInfo getPlayers(PageReq<PlayerReq> pageReq);

    PlayerResp getPlayerByName(String playerName);

    PlayerResp getPlayerByNick(String playerNick);

    PlayerResp getPlayerByNameOrNick(String nameOrNick);

    Player getPlayerByInvite(String invite);

    String getPlayerNickByPlayerId(String playerId);


    /**
     * 获取玩家等级
     * @param playerId
     * @return
     */
    PlayerGrade getPlayerGradeByPlayerId(String playerId);


}
