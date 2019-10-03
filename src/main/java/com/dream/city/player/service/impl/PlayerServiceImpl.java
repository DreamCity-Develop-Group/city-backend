package com.dream.city.player.service.impl;

import com.dream.city.base.PageReq;
import com.dream.city.player.dao.PlayerGradeMapper;
import com.dream.city.player.dao.PlayerMapper;
import com.dream.city.player.dto.PlayerReq;
import com.dream.city.player.entity.Player;
import com.dream.city.player.entity.PlayerGrade;
import com.dream.city.player.service.FriendsService;
import com.dream.city.player.service.PlayerService;
import com.dream.city.util.DataUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author Wvv
 */
@Service
public class PlayerServiceImpl implements PlayerService {

    @Autowired
    private PlayerMapper playerMapper;
    @Autowired
    private PlayerGradeMapper gradeMapper;
    @Autowired
    private FriendsService friendsService;

    @Value("${spring.application.name}")
    private String appName;


    @Override
    public Player getPlayer(Player player) {
        return playerMapper.getPlayerById(player);
    }

    @Override
    public Player getPlayerByrId(Long id) {
        if (id == null){
            return null;
        }
        if (id <= 0){
            return null;
        }
        Player player = new Player();
        player.setId(id);
        return playerMapper.getPlayerById(player);
    }


    @Override
    public Player getPlayerByPlayerId(String playerId) {
        Player player = new Player();
        if (StringUtils.isBlank(playerId)){
            return null;
        }
        player.setPlayerId(playerId);
        return playerMapper.getPlayerById(player);
    }


    @Override
    public PageInfo getPlayers(PageReq<PlayerReq> pageReq) {
        Player playerReq = DataUtils.getData(pageReq.getCondition(),Player.class);
        PageHelper.startPage(pageReq.getPageNum(),pageReq.getPageSize(),pageReq.isCount());
        List<Map> players = playerMapper.getPlayers(playerReq);
        return new PageInfo<>(players);
    }

    @Override
    public Player getPlayerByName(String playerName) {
        Player playerResp = null;
        if (StringUtils.isNotBlank(playerName)) {
            Player playerReq = new Player();
            playerReq.setPlayerName(playerName);
            playerResp = playerMapper.getPlayerById(playerReq);
        }
        return playerResp;
    }


    @Override
    public Player getPlayerByNick(String playerNick) {
        Player playerResp = null;
        if (StringUtils.isNotBlank(playerNick)) {
            Player playerReq = new Player();
            playerReq.setPlayerNick(playerNick);
            playerResp = playerMapper.getPlayerById(playerReq);
        }
        return playerResp;
    }

    @Override
    public Player getPlayerByNameOrNick(String nameOrNick) {
        Player playerResp = null;
        if (StringUtils.isNotBlank(nameOrNick)) {
            Player nameReq = new Player();
            nameReq.setPlayerName(nameOrNick);
            playerResp = playerMapper.getPlayerById(nameReq);
            if (playerResp == null){
                Player nickReq = new Player();
                nickReq.setPlayerNick(nameOrNick);
                playerResp = playerMapper.getPlayerById(nickReq);
            }
        }
        return playerResp;
    }

    @Override
    public Player getPlayerByInvite(String invite){
        Player player = playerMapper.getPlayerByInvite(invite);
        return player;
    }

    @Override
    public String getPlayerNickByPlayerId(String playerId) {
        Player player = getPlayerByPlayerId(playerId);
        if (player == null) {
            return null;
        }
        return player.getPlayerNick();
    }


    @Override
    public PlayerGrade getPlayerGradeByPlayerId(String playerId) {
        PlayerGrade record = new PlayerGrade();
        record.setPlayerId(playerId);
        return gradeMapper.getPlayerGradeByPlayerId(record);
    }


}
