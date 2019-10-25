package com.dream.city.service.account;


import com.dream.city.base.model.Page;
import com.dream.city.base.model.entity.PlayerAccount;
import com.dream.city.base.model.req.PlayerAccountReq;
import com.dream.city.base.model.resp.PlayerAccountResp;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * 玩家账户
 */
public interface AccountService {

    /**
     * 新增玩家账户
     * @param record
     * @return
     */
    int createPlayerAccount(PlayerAccount record);

    /**
     * 获取玩家账户
     * @param record
     * @return
     */
    PlayerAccountResp getPlayerAccount(PlayerAccountReq record);

    /**
     * 玩家账户列表
     * @param record
     * @return
     */
    PageInfo<PlayerAccountResp> getPlayerAccountList(Page pageReq, PlayerAccountReq record);

    /**
     * 更新玩家账户
     * @param record
     * @return
     */
    int updatePlayerAccount(PlayerAccount record);


    /**
     * 获取平台账户
     * @param record
     * @return
     */
    List<PlayerAccount> getPlatformAccounts(PlayerAccountReq record);


}
