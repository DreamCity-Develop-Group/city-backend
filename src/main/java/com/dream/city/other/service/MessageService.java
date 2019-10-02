package com.dream.city.other.service;


import com.dream.city.base.PageReq;
import com.dream.city.other.dto.MessageReq;
import com.dream.city.other.dto.MessageResp;
import com.dream.city.other.entity.CityMessage;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface MessageService {

    int insertMessage(MessageReq record);

    int updateMessageById(MessageReq record);

    int updateMessageHaveReadById(CityMessage record);

    int deleteMessageById(Long id);

    MessageResp getMessageById(Long id);

    PageInfo<MessageResp> getCityMessageList(PageReq<MessageReq> record);
}