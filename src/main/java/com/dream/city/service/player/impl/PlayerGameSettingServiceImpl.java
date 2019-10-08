package com.dream.city.service.player.impl;

import com.dream.city.base.model.Page;
import com.dream.city.base.model.entity.PlayerGameSetting;
import com.dream.city.base.model.mapper.PlayerGameSettingMapper;
import com.dream.city.base.model.req.PlayerGameSettingReq;
import com.dream.city.base.model.resp.PlayerGameSettingResp;
import com.dream.city.base.model.resp.PlayerResp;
import com.dream.city.base.utils.DataUtils;
import com.dream.city.service.player.PlayerGameSettingService;
import com.dream.city.service.player.PlayerService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * @author
 */
@Service
public class PlayerGameSettingServiceImpl implements PlayerGameSettingService {

    @Autowired
    private PlayerGameSettingMapper playerGameSettingMapper;
    @Autowired
    private PlayerService playerService;


    @Override
    public int deleteGameSettingById(Long id) {
        return playerGameSettingMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int updateGameSettingById(PlayerGameSetting record) {
        return playerGameSettingMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int insertGameSetting(PlayerGameSetting record) {
        return playerGameSettingMapper.insertSelective(record);
    }

    @Override
    public PlayerGameSettingResp getGameSettingById(Long id) {
        PlayerGameSetting setting = playerGameSettingMapper.selectByPrimaryKey(id);
        return geSettingResp(setting);
    }

    @Override
    public PageInfo<PlayerGameSettingResp> getGameSettingList(Page record) {
        PlayerGameSettingReq settingReq = DataUtils.toJavaObject(record.getCondition(),PlayerGameSettingReq.class);
        PlayerResp getPlayer = playerService.getPlayerByNameOrNick(settingReq.getPlayerNick());
        PlayerGameSetting playerGameSettingReq = DataUtils.getData(record.getCondition(),PlayerGameSetting.class);

        PageHelper.startPage(record.getPageNum(),record.getPageSize(),record.isCount());
        if (getPlayer != null){
            playerGameSettingReq.setPlayerId(getPlayer.getPlayerId());
        }

        List<PlayerGameSetting> settingList = playerGameSettingMapper.getGameSettingList(playerGameSettingReq);
        List<PlayerGameSettingResp> settingRespList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(settingList)){
            for (PlayerGameSetting setting : settingList){
                PlayerGameSettingResp settingResp = geSettingResp(setting);
                settingRespList.add(settingResp);
            }
        }
        return new PageInfo<>(settingRespList);
    }

    private PlayerGameSettingResp geSettingResp(PlayerGameSetting setting){
        String nick = playerService.getPlayerNickByPlayerId(setting.getPlayerId());
        PlayerGameSettingResp settingResp = DataUtils.getData(setting,PlayerGameSettingResp.class);
        settingResp.setPlayerNick(nick);
        return settingResp;
    }
}
