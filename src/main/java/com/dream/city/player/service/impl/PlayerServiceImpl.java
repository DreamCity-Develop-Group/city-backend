package com.dream.city.player.service.impl;

import com.alibaba.fastjson.JSON;
import com.dream.city.player.dao.PlayerGradeMapper;
import com.dream.city.player.dao.PlayerMapper;
import com.dream.city.player.entity.Friends;
import com.dream.city.player.entity.Player;
import com.dream.city.player.entity.PlayerGrade;
import com.dream.city.player.service.FriendsService;
import com.dream.city.player.service.PlayerService;
import com.github.pagehelper.Page;
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
    public Player update(Player player) {
        int i = playerMapper.updateByPlayerId(player);
        return i > 0? player: null;
    }

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
    public Page getPlayers(Page pageReq) {//todo
        Page page = new Page();

        Integer count = playerMapper.getPlayersCount(pageReq);
        List<Map> players = playerMapper.getPlayers(pageReq);
        return page;
    }

    private String getFriendAgree(String playerId,Map player){
        String friendId = player.containsKey("player")?String.valueOf(player.get("player")): null;
        Friends record = new Friends();
        record.setPlayerId(playerId);
        record.setFriendId(friendId);
        Integer getFriendAgree = friendsService.getFriendAgree(record);

        String result = "添加";
        if (getFriendAgree == null){
            result = "添加";
        }else {
            if (getFriendAgree == 0){
                result = "已申请";
            } else if (getFriendAgree == 1){
                result = "已添加";
            }
        }
        return result;
    }

    @Override
    public Player getPlayerByName(String playerName,String playerNick) {
        Player player = new Player();
        if (StringUtils.isNotBlank(playerName)) {
            player.setPlayerName(playerName);
        }
        if (StringUtils.isNotBlank(playerNick)) {
            player.setPlayerNick(playerNick);
        }
        Player playerByName = null;
        if (StringUtils.isNotBlank(playerName) || StringUtils.isNotBlank(playerNick)) {
            playerByName = playerMapper.getPlayerById(player);
        }
        Player playerResp = null;
        if (playerByName != null){
            playerResp = JSON.toJavaObject(JSON.parseObject(JSON.toJSONString(playerByName)),Player.class);

            /*这里不用查询级别
            PlayerGrade playerGrade = getPlayerGradeByPlayerId(player.getPlayerId());
            playerResp.setGrade(playerGrade.getGrade());
            playerResp.setCommerceMember(0); //商会成员数 todo
            */
            return playerResp;
        }
        return playerResp;
    }

    @Override
    public Player getPlayerByInvite(String invite){
        Player player = playerMapper.getPlayerByInvite(invite);
        return player;
    }

    @Override
    public Player getPlayerByAccount(String account){
        Player player = playerMapper.getPlayerByAccount(account);
        return player;
    }

    @Override
    public PlayerGrade getPlayerGradeByPlayerId(String playerId) {
        PlayerGrade record = new PlayerGrade();
        record.setPlayerId(playerId);
        return gradeMapper.getPlayerGradeByPlayerId(record);
    }


}
