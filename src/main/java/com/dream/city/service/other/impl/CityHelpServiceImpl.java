package com.dream.city.service.other.impl;

import com.dream.city.base.model.Page;
import com.dream.city.base.model.entity.CityHelp;
import com.dream.city.base.model.mapper.CityHelpMapper;
import com.dream.city.base.utils.DataUtils;
import com.dream.city.service.other.CityHelpService;
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
    public PageInfo<CityHelp> getCityHelpList(Page record) {
        CityHelp help = DataUtils.toJavaObject(record.getCondition(),CityHelp.class);
        PageHelper.startPage(record.getPageNum(), record.getPageSize(),record.isCount());
        List<CityHelp> cityHelps = helpMapper.selectCityHelpList(help);
        return new PageInfo<>(cityHelps);
    }

    @Override
    public Integer updateCityHelpById(CityHelp record) {
        return helpMapper.updateByPrimaryKeySelective(record);
    }
}
