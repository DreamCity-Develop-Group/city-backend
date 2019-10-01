package com.dream.city.other.service;


import com.dream.city.other.entity.CityHelp;

import java.util.List;

public interface CityHelpService {

    Integer deleteCityHelpById(Integer id);

    Integer insertCityHelp(CityHelp record);

    CityHelp getCityHelpById(Integer id);

    List<CityHelp> getCityHelpList(CityHelp record);

    Integer updateCityHelpById(CityHelp record);


}