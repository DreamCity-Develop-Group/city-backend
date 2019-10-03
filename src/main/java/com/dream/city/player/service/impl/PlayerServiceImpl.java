package com.dream.city.player.service.impl;

import com.dream.city.base.PageReq;
import com.dream.city.player.dao.PlayerGradeMapper;
import com.dream.city.player.dao.PlayerMapper;
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
    public Player getPlayerById(String playerId) {
        Player player = new Player();
        if (StringUtils.isBlank(playerId)){
            return null;
        }
        player.setPlayerId(playerId);
        return playerMapper.getPlayerById(player);
    }


    @Override
    public PageInfo getPlayers(PageReq pageReq) {
        PageHelper.startPage(pageReq.getPageNum(),pageReq.getPageSize(),pageReq.isCount());
        Player player = DataUtils.toJavaObject(pageReq.getCondition(),Player.class);
        List<Map> players = playerMapper.getPlayers(player);
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
    public Player getPlayerByInvite(String invite){
        Player player = playerMapper.getPlayerByInvite(invite);
        return player;
    }


    @Override
    public PlayerGrade getPlayerGradeByPlayerId(String playerId) {
        PlayerGrade record = new PlayerGrade();
        record.setPlayerId(playerId);
        return gradeMapper.getPlayerGradeByPlayerId(record);
    }


}
