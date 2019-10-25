package com.dream.city.service.player;

import com.dream.city.base.model.Page;
import com.dream.city.base.model.entity.*;
import com.dream.city.base.model.resp.PlayerAccountResp;
import com.dream.city.base.model.resp.PlayerResp;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;


/**
 * @author Wvv
 */
@Repository
public interface PlayerService {


    PlayerResp getPlayer(Player player);

    PlayerResp getPlayerByrId(Long id);

    Player selectPlayerId(Long id);

    PlayerResp getPlayerByPlayerId(String playerId);

    /**
     * 玩家列表
     * @param pageReq
     * @return
     */
    PageInfo getPlayers(Page pageReq);

    PageInfo<Genesis> getGenesis(Page pageReq);

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


    PlayerAccount getPlayerAccountRespForId(String playerId);

    Player getPlayerId(String playerId);

    Integer  updateGenesisPlayer(String playerId,String accAddr);

    Integer add(Genesis genesis);

    //List<Genesis> getListGenesis();

    Genesis getGenesis(String playerId);
}
