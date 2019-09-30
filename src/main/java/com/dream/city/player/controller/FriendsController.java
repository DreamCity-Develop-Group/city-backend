package com.dream.city.player.controller;


import com.dream.city.player.service.FriendsService;
import com.github.pagehelper.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/friends")
public class FriendsController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private FriendsService friendsService;


    /**
     * 好友列表
     * @param pageReq
     * @return
     */
    @RequestMapping("/friendList")
    public Page friendList(@RequestBody Page pageReq){
        logger.info("friendList，pageReq：{}",pageReq);
        Page page = friendsService.friendList(pageReq);
        return page;
    }


    /**
     * 好友申请列表
     * @param pageReq
     * @return
     */
    @RequestMapping("/applyFriendList")
    public Page applyFriendList(@RequestBody Page pageReq){
        logger.info("applyFriendList，pageReq：{}",pageReq);
        Page page = friendsService.applyFriendList(pageReq);
        return page;
    }



}
