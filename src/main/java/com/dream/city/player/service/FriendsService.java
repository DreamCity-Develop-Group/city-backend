package com.dream.city.player.service;


import com.dream.city.base.PageReq;
import com.dream.city.player.dto.FriendsReq;
import com.dream.city.player.dto.FriendsResp;
import com.dream.city.player.entity.Friends;
import com.github.pagehelper.PageInfo;

/**
 * 好友
 */
public interface FriendsService {

    /**
     * 添加好友
     * @param friend
     * @return
     */
    boolean addFriend(Friends friend);

    /**
     * 同意添加好友
     * @param friend
     * @return
     */
    boolean agreeAddFriend(Friends friend);

    /**
     * 好友列表
     * @param pageReq
     * @return
     */
    PageInfo friendList(PageReq<FriendsReq> pageReq);

    /**
     * 申请列表
     * @param pageReq
     * @return
     */
    PageInfo applyFriendList(PageReq<FriendsReq> pageReq);

    FriendsResp getFriendById(Long id);

    Integer getFriendAgree(Friends record);
}
