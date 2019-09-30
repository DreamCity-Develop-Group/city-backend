package com.dream.city.player.service;


import com.dream.city.player.entity.Friends;
import com.github.pagehelper.Page;

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
    Page friendList(Page pageReq);

    /**
     * 申请列表
     * @param pageReq
     * @return
     */
    Page applyFriendList(Page pageReq);

    Friends selectByPlayerIdFriendId(Friends record);

    Integer getFriendAgree(Friends record);
}
