package com.dream.city.player.service.impl;

import com.dream.city.base.model.Page;
import com.dream.city.base.model.entity.Friends;
import com.dream.city.base.model.mapper.FriendsMapper;
import com.dream.city.base.model.req.FriendsReq;
import com.dream.city.base.model.resp.FriendsResp;
import com.dream.city.base.utils.DataUtils;
import com.dream.city.player.service.FriendsService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public PageInfo friendList(Page pageReq) {// TODO
        FriendsReq friendsReq = DataUtils.toJavaObject(pageReq.getCondition(),FriendsReq.class);
        PageHelper.startPage(pageReq.getPageNum(),pageReq.getPageSize());
        List<FriendsResp> friendList = friendsMapper.friendList(friendsReq);
        return new PageInfo(friendList);
    }

    /*@Override
    public PageInfo applyFriendList(Page  pageReq) {// TODO
        FriendsReq friendsReq = DataUtils.toJavaObject(pageReq.getCondition(),FriendsReq.class);
        PageHelper.startPage(pageReq.getPageNum(),pageReq.getPageSize());
        List<FriendsResp> friendList = friendsMapper.applyFriendList(friendsReq);
        return new PageInfo(friendList);
    }*/

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
