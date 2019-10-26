package com.dream.city.service.trade;


import com.dream.city.base.model.entity.PlayerEarning;
import com.dream.city.base.model.resp.PlayerEarningResp;

import java.util.List;

/**
 * 玩家提现收入
 */
public interface EarningService {

    /**
     * 删除玩家提现收入
     * @param earnId
     * @return
     */
    //int deleteEarningById(Integer earnId);

    /**
     * 新增玩家提现收入
     * @param record
     * @return
     */
    //int insertEarning(PlayerEarning record);

    /**
     * 查询玩家提现收入
     * @param earnId
     * @return
     */
    PlayerEarning getEarningById(Integer earnId);

    PlayerEarningResp getEarning(PlayerEarning record);


    /**
     * 查询玩家提现收入列表
     * @param record
     * @return
     */
    List<PlayerEarning> getEarningList(PlayerEarning record);

    /**
     * 更新玩家提现收入
     * @param record
     * @return
     */
    int updateEarningById(PlayerEarning record);

}
