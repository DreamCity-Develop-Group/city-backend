package com.dream.city.account.dao;


import com.dream.city.account.entity.PlayerAccount;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PlayerAccountMapper {

    Integer deleteByPrimaryKey(Integer accId);

    Integer insertSelective(PlayerAccount record);

    PlayerAccount getPlayerAccount(PlayerAccount record);

    /**
     * 获取平台账户
     * @param record
     * @return
     */
    List<PlayerAccount> getPlatformAccounts(PlayerAccount record);

    Integer updateByPlayerId(PlayerAccount record);

    List<PlayerAccount> getPlayerAccountList(PlayerAccount record);

}