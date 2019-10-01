package com.dream.city.other.service.impl;

import com.dream.city.base.PageReq;
import com.dream.city.other.dao.CityHelpMapper;
import com.dream.city.other.entity.CityHelp;
import com.dream.city.other.service.CityHelpService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author
 */
@Service
public class CityHelpServiceImpl implements CityHelpService {

    @Autowired
    CityHelpMapper helpMapper;

    @Override
    public Integer deleteCityHelpById(Integer id) {
        return helpMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Integer insertCityHelp(CityHelp record) {
        return helpMapper.insertSelective(record);
    }

    @Override
    public CityHelp getCityHelpById(Integer id) {
        return helpMapper.selectByPrimaryKey(id);
    }

    @Override
    public PageInfo<CityHelp> getCityHelpList(PageReq<CityHelp> record) {
        PageHelper.startPage(record.getPageNum(), record.getPageSize());
        List<CityHelp> cityHelps = helpMapper.selectCityHelpList(record.getCondition());
        PageInfo<CityHelp> pageInfo = new PageInfo<>(cityHelps);
        return new PageInfo<>(cityHelps);
    }

    @Override
    public Integer updateCityHelpById(CityHelp record) {
        return helpMapper.updateByPrimaryKeySelective(record);
    }
}
