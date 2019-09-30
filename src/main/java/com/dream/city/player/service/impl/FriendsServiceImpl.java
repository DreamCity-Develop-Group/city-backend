package com.dream.city.player.service.impl;

import com.dream.city.player.dao.FriendsMapper;
import com.dream.city.player.entity.Friends;
import com.dream.city.player.service.FriendsService;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class FriendsServiceImpl implements FriendsService {

    @Autowired
    private FriendsMapper friendsMapper;


    @Override
    public boolean addFriend(Friends friend) {
        friend.setAgree(0);
        return friendsMapper.insertSelective(friend)>0?Boolean.TRUE:Boolean.FALSE;
    }

    @Override
    public boolean agreeAddFriend(Friends friend) {
        return friendsMapper.agreeAddFriend(friend)>0?Boolean.TRUE:Boolean.FALSE;
    }

    @Override
    public Page friendList(Page pageReq) {// TODO
        Page<Map> page = new Page<>();

        Integer count = friendsMapper.friendCount(pageReq);
        List<Map> friendList = friendsMapper.friendList(pageReq);
        return page;
    }

    @Override
    public Page applyFriendList(Page pageReq) {// TODO
        Page<Map> page = new Page<>();

        Integer count = friendsMapper.applyFriendCount(pageReq);
        List<Map> friendList = friendsMapper.applyFriendList(pageReq);
        return page;
    }

    @Override
    public Friends selectByPlayerIdFriendId(Friends record) {
        return friendsMapper.selectByPlayerIdFriendId(record);
    }

    @Override
    public Integer getFriendAgree(Friends record) {
        return friendsMapper.getFriendAgree(record);
    }
}
