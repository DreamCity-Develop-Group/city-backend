package com.dream.city.player.dao;


import com.dream.city.player.dto.FriendsReq;
import com.dream.city.player.dto.FriendsResp;
import com.dream.city.player.entity.Friends;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FriendsMapper {

    int deleteByPrimaryKey(Long id);


    int insertSelective(Friends record);

    Friends selectByPrimaryKey(Long id);

    FriendsResp getFriend(Friends record);

    int updateByPrimaryKeySelective(Friends record);

    /**
     * 同意添加好友
     * @param record
     * @return
     */
    int agreeAddFriend(Friends record);

    /**
     * 好友列表
     * @param page
     * @return
     */
    List<FriendsResp> friendList(FriendsReq page);


    /**
     * 申请列表
     * @param page
     * @return
     */
    List<FriendsResp> applyFriendList(FriendsReq page);

    Integer getFriendAgree(Friends record);
}