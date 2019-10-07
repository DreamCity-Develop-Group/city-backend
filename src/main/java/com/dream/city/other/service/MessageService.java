package com.dream.city.other.service;


import com.dream.city.base.model.Page;
import com.dream.city.base.model.entity.CityMessage;
import com.dream.city.base.model.req.MessageReq;
import com.dream.city.base.model.resp.MessageResp;
import com.github.pagehelper.PageInfo;

public interface MessageService {

    int insertMessage(MessageReq record);

    int updateMessageById(MessageReq record);

    int updateMessageHaveReadById(CityMessage record);

    int deleteMessageById(Long id);

    MessageResp getMessageById(Long id);

    PageInfo<MessageResp> getCityMessageList(Page record);
}