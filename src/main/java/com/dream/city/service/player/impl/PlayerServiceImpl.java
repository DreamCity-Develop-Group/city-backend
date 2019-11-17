package com.dream.city.service.player.impl;

import com.dream.city.base.model.Page;
import com.dream.city.base.model.entity.*;
import com.dream.city.base.model.mapper.GenesisMapper;
import com.dream.city.base.model.mapper.PlayerAccountMapper;
import com.dream.city.base.model.mapper.PlayerGradeMapper;
import com.dream.city.base.model.mapper.PlayerMapper;
import com.dream.city.base.model.resp.PlayerAccountResp;
import com.dream.city.base.model.resp.PlayerResp;
import com.dream.city.base.utils.DataUtils;
import com.dream.city.service.player.FriendsService;
import com.dream.city.service.player.PlayerService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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

    @Autowired
    private PlayerAccountMapper playerAccountMapper;

    @Autowired
    private GenesisMapper genesisMapper;

    @Value("${spring.application.name}")
    private String appName;


    @Override
    public PlayerResp getPlayer(Player player) {
        return playerMapper.getPlayerById(player);
    }



    @Override
    public PlayerResp getPlayerByrId(Long id) {
        if (id == null){
            return null;
        }
        if (id <= 0){
            return null;
        }
        Player player = new Player();
        player.setId(id);
        PlayerResp resp =playerMapper.getPlayerById(player);
        return resp;
    }

    @Override
    public Player selectPlayerId(Long id) {
        if (id == null){
            return null;
        }
        if (id <= 0){
            return null;
        }
        Player pp = playerMapper.selectPlayerId(Integer.valueOf(id.toString()));
        return pp;
    }


    @Override
    public PlayerResp getPlayerByPlayerId(String playerId) {
        Player player = new Player();
        if (StringUtils.isBlank(playerId)){
            return null;
        }
        player.setPlayerId(playerId);
        return playerMapper.getPlayerById(player);
    }


    @Override
    public PageInfo getPlayers(Page pageReq) {
        Player playerReq = DataUtils.getData(pageReq.getCondition(),Player.class);
        PageHelper.startPage(pageReq.getPageNum(),pageReq.getPageSize(),pageReq.isCount());
        List<PlayerResp> players = playerMapper.getPlayerList(playerReq);
        return new PageInfo<>(players);
    }

    @Override
    public PageInfo getGenesis(Page pageReq){
         Genesis item = DataUtils.toJavaObject(pageReq.getCondition(),Genesis.class);
         PageHelper.startPage(pageReq.getPageNum(),pageReq.getPageSize(),pageReq.isCount());
        List<Genesis> list = genesisMapper.getListGenesis(item);
        System.out.println("*-----------------------*"+list);
         return  new PageInfo<>(list);

    }



    @Override
    public PlayerResp getPlayerByName(String playerName) {
        PlayerResp playerResp = null;
        if (StringUtils.isNotBlank(playerName)) {
            Player playerReq = new Player();
            playerReq.setPlayerName(playerName);
            playerResp = playerMapper.getPlayerById(playerReq);
        }
        return playerResp;
    }


    @Override
    public PlayerResp getPlayerByNick(String playerNick) {
        PlayerResp playerResp = null;
        if (StringUtils.isNotBlank(playerNick)) {
            Player playerReq = new Player();
            playerReq.setPlayerNick(playerNick);
            playerResp = playerMapper.getPlayerById(playerReq);
        }
        return playerResp;
    }

    @Override
    public PlayerResp getPlayerByNameOrNick(String nameOrNick) {
        PlayerResp playerResp = null;
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
        PlayerResp player = getPlayerByPlayerId(playerId);
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



    @Override
    public PlayerAccount getPlayerAccountRespForId(String playerId) {
        PlayerAccount playerResp = null;
        if (StringUtils.isNotBlank(playerId)) {
            //PlayerAccount PlayerAccountResp = new PlayerAccountResp();
            //PlayerAccountResp.setPlayerId(playerId);
            playerResp = playerAccountMapper.getAccountByPlayerId(playerId);
        }
        return playerResp;
    }


    @Override
    public Player getPlayerId(String playerId){
        return playerMapper.getPlayer(playerId);
    }

    @Override
    public Integer updateGenesisPlayer(String playerId, String accAddr) {
        return genesisMapper.updateGenesis(playerId,accAddr);
    }

    @Override
    public Integer add(Genesis genesis){
        return genesisMapper.add(genesis);
    }


   /* @Override
    public  List<Genesis> getListGenesis(Genesis gnesis){
        return genesisMapper.getListGenesis(genesis);
    }*/

    @Override
    public Genesis getGenesis(String playerId){
        return genesisMapper.getGenesis(playerId);
    }

}
