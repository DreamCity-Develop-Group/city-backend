package com.dream.city.service.player;


import com.dream.city.base.model.Page;
import com.dream.city.base.model.entity.Friends;
import com.dream.city.base.model.req.FriendsReq;
import com.dream.city.base.model.resp.FriendsResp;
import com.github.pagehelper.PageInfo;

import java.util.List;

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
    PageInfo friendList(Page pageReq);

    //PageInfo applyFriendList(Page pageReq);

    FriendsResp getFriendById(Long id);

    Integer getFriendAgree(Friends record);

    PageInfo<FriendsResp> getFriendsAll(Page pageReq);
}
