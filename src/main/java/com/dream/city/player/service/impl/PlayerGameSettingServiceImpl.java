package com.dream.city.player.service.impl;

import com.dream.city.base.PageReq;
import com.dream.city.player.dao.PlayerGameSettingMapper;
import com.dream.city.player.dto.PlayerGameSettingReq;
import com.dream.city.player.dto.PlayerGameSettingResp;
import com.dream.city.player.dto.PlayerResp;
import com.dream.city.player.entity.PlayerGameSetting;
import com.dream.city.player.service.PlayerGameSettingService;
import com.dream.city.player.service.PlayerService;
import com.dream.city.util.DataUtils;
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
    public PageInfo<PlayerGameSettingResp> getGameSettingList(PageReq<PlayerGameSettingReq> record) {
        PlayerResp getPlayer = playerService.getPlayerByNameOrNick(record.getCondition().getPlayerNick());
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
