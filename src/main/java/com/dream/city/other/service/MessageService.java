package com.dream.city.other.service;


import com.dream.city.other.entity.CityMessage;

import java.util.List;

public interface MessageService {

    int insertMessage(CityMessage record);

    int updateMessageById(CityMessage record);

    int updateMessageHaveReadById(CityMessage record);

    int deleteMessageById(Long id);

    CityMessage getMessageById(Long id);

    List<CityMessage> getCityMessageList(CityMessage record);
}