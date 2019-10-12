package com.dream.city.service.verify.impl;

import com.dream.city.base.model.entity.TradeVerify;
import com.dream.city.base.model.mapper.TradeVerifyMapper;
import com.dream.city.service.verify.TradeVerifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TradeVerifyServiceImpl implements TradeVerifyService {

    @Autowired
    TradeVerifyMapper verifyMapper;

    @Override
    public TradeVerify insertTradeVerify(TradeVerify record) {
        Integer integer = verifyMapper.insertSelective(record);
        return (integer == null || integer < 1)? null:record;
    }

    @Override
    @Transactional
    public int updateTradeVerify(TradeVerify record) {
        Integer integer = verifyMapper.updateByPrimaryKeySelective(record);
        return integer == null? 0: integer;
    }

    @Override
    public TradeVerify getTradeVerifyBiId(Integer verifyId) {
        return verifyMapper.getTradeVerifyBiId(verifyId);
    }

    @Override
    public TradeVerify getTradeVerify(TradeVerify record) {
        return verifyMapper.getTradeVerify(record);
    }

    @Override
    public List<TradeVerify> getTradeVerifyList(TradeVerify record) {
        return verifyMapper.getTradeVerifyList(record);
    }
}
