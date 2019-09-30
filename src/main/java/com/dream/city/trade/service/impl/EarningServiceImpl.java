package com.dream.city.trade.service.impl;

import com.dream.city.trade.dao.PlayerEarningMapper;
import com.dream.city.trade.entity.PlayerEarning;
import com.dream.city.trade.service.EarningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EarningServiceImpl implements EarningService {

    @Autowired
    private PlayerEarningMapper earningMapper;

    @Override
    public int deleteEarningById(Integer earnId) {
        Integer i = earningMapper.deleteByPrimaryKey(earnId);
        return i == null? 0: i;
    }

    @Override
    public int insertEarning(PlayerEarning record) {
        Integer i = earningMapper.insertSelective(record);
        return i == null? 0: i;
    }

    @Override
    public PlayerEarning getEarning(Integer earnId) {
        return earningMapper.selectByPrimaryKey(earnId);
    }

    @Override
    public PlayerEarning getPlayerEarningByPlayerId(String playerId) {
        PlayerEarning record = new PlayerEarning();
        record.setEarnPlayerId(playerId);
        return earningMapper.getPlayerEarningByPlayerId(record);
    }

    @Override
    public List<PlayerEarning> getEarningList(PlayerEarning record) {
        return earningMapper.selectPlayerEarningList(record);
    }

    @Override
    public int updateEarningById(PlayerEarning record) {
        Integer i = earningMapper.updateByPrimaryKeySelective(record);
        return i == null? 0: i;
    }
}
