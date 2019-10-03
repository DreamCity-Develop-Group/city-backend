package com.dream.city.player.controller;

import com.dream.city.base.PageReq;
import com.dream.city.player.entity.Player;
import com.dream.city.player.service.LoginLogServcie;
import com.dream.city.player.service.PlayerExtService;
import com.dream.city.player.service.PlayerService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 玩家
 * @author Wvv
 */
@Slf4j
@RestController
@RequestMapping("/player")
public class PlayerController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    PlayerService playerService;
    @Autowired
    LoginLogServcie loginLogServcie;
    @Autowired
    PlayerExtService playerExtService;



    /**
     * 玩家
     * @param playerId
     * @return
     */
    @RequestMapping("/get/{playerId}")
    public Player getPlayer(@PathVariable("playerId")String playerId){
        log.info("获取玩家，playerId:{}",playerId);
        return playerService.getPlayerById(playerId);
    }


    /**
     * 广场玩家列表
     * @param pageReq
     * @return
     */
    @RequestMapping("/getPlayers")
    public PageInfo getPlayers(@RequestBody PageReq pageReq){
        log.info("获取广场玩家列表，{}",pageReq);
        PageInfo page = null;
        try {
            page = playerService.getPlayers(pageReq);
        }catch (Exception e){
            logger.error("获取广场玩家列表异常",e);
        }
        return page;
    }


    @RequestMapping(value = "/getPlayerByName",method = RequestMethod.POST,produces="application/json; utf-8")
    public Player getPlayerByName(@RequestBody Player playerReq){
        log.info("根据用户名或昵称获取玩家，{}",playerReq);
        Player player = playerService.getPlayerByName(playerReq.getPlayerName());
        if (player == null){
            player = playerService.getPlayerByNick(playerReq.getPlayerNick());
        }
        return player;
    }


}
