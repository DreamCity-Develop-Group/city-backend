package com.dream.city.service.player;

import com.dream.city.base.model.Page;
import com.dream.city.base.model.entity.Player;
import com.dream.city.base.model.entity.PlayerGrade;
import com.dream.city.base.model.resp.PlayerResp;
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
    PageInfo getPlayers(Page pageReq);

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
