package com.dream.city.other.service;

import com.dream.city.base.PageReq;
import com.dream.city.other.entity.CityHelp;
import com.github.pagehelper.PageInfo;

public interface CityHelpService {

    Integer deleteCityHelpById(Integer id);

    Integer insertCityHelp(CityHelp record);

    CityHelp getCityHelpById(Integer id);

    PageInfo<CityHelp> getCityHelpList(PageReq<CityHelp> record);

    Integer updateCityHelpById(CityHelp record);


}