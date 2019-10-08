package com.dream.city.service.other;

import com.dream.city.base.model.Page;
import com.dream.city.base.model.entity.CityHelp;
import com.github.pagehelper.PageInfo;

public interface CityHelpService {

    Integer deleteCityHelpById(Integer id);

    Integer insertCityHelp(CityHelp record);

    CityHelp getCityHelpById(Integer id);

    PageInfo<CityHelp> getCityHelpList(Page record);

    Integer updateCityHelpById(CityHelp record);


}