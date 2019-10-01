package com.dream.city.other.service.impl;

import com.dream.city.other.dao.CityHelpMapper;
import com.dream.city.other.entity.CityHelp;
import com.dream.city.other.service.CityHelpService;
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
    public List<CityHelp> getCityHelpList(CityHelp record) {
        return helpMapper.selectCityHelpList(record);
    }

    @Override
    public Integer updateCityHelpById(CityHelp record) {
        return helpMapper.updateByPrimaryKeySelective(record);
    }
}
