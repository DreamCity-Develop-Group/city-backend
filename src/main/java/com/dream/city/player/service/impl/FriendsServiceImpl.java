package com.dream.city.player.service.impl;

import com.dream.city.base.PageReq;
import com.dream.city.player.dao.FriendsMapper;
import com.dream.city.player.dto.FriendsReq;
import com.dream.city.player.dto.FriendsResp;
import com.dream.city.player.dto.PlayerReq;
import com.dream.city.player.entity.Friends;
import com.dream.city.player.service.FriendsService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
    public PageInfo friendList(PageReq<FriendsReq> pageReq) {// TODO
        PageHelper.startPage(pageReq.getPageNum(),pageReq.getPageSize());
        List<FriendsResp> friendList = friendsMapper.friendList(pageReq.getCondition());
        return new PageInfo(friendList);
    }

    @Override
    public PageInfo applyFriendList(PageReq<FriendsReq> pageReq) {// TODO
        PageHelper.startPage(pageReq.getPageNum(),pageReq.getPageSize());
        List<FriendsResp> friendList = friendsMapper.applyFriendList(pageReq.getCondition());
        return new PageInfo(friendList);
    }

    @Override
    public FriendsResp getFriendById(Long id) {
        Friends friend = new Friends();
        friend.setId(id);
        return friendsMapper.getFriend(friend);
    }

    @Override
    public Integer getFriendAgree(Friends record) {
        return friendsMapper.getFriendAgree(record);
    }
}
