package com.dream.city.other.service.impl;

import com.dream.city.base.model.Page;
import com.dream.city.base.model.entity.CityMessage;
import com.dream.city.base.model.mapper.MessageMapper;
import com.dream.city.base.model.req.MessageReq;
import com.dream.city.base.model.resp.MessageResp;
import com.dream.city.base.model.resp.PlayerResp;
import com.dream.city.base.utils.DataUtils;
import com.dream.city.other.service.MessageService;
import com.dream.city.player.service.PlayerService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {


    @Autowired
    MessageMapper messageMapper;
    @Autowired
    PlayerService playerService;


    @Override
    public int insertMessage(MessageReq record) {
        CityMessage message = this.getMessage(record);
        Integer integer = messageMapper.insertSelective(message);
        return integer == null? 0:integer;
    }

    @Override
    public int updateMessageById(MessageReq record) {
        CityMessage message = this.getMessage(record);
        Integer integer = messageMapper.updateByPrimaryKeySelective(message);
        return integer == null? 0:integer;
    }

    @Override
    public int updateMessageHaveReadById(CityMessage record) {
        MessageReq updateMessage = new MessageReq();
        updateMessage.setId(record.getId());
        updateMessage.setPlayerId(record.getPlayerId());
        updateMessage.setHaveRead(1);
        return updateMessageById(updateMessage);
    }

    @Override
    public int deleteMessageById(Long id) {
        Integer integer = messageMapper.deleteByPrimaryKey(id);
        return integer == null? 0:integer;
    }

    @Override
    public MessageResp getMessageById(Long id) {
        return this.getMessageResp(messageMapper.selectByPrimaryKey(id));
    }

    @Override
    public PageInfo<MessageResp> getCityMessageList(Page record) {
        MessageReq messageReq = DataUtils.toJavaObject(record.getCondition(),MessageReq.class);
        CityMessage getCityMessageListReq = this.getMessage(messageReq);
        PageHelper.startPage(record.getPageNum(), record.getPageSize(),record.isCount());
        List<CityMessage> messageList = messageMapper.getCityMessageList(getCityMessageListReq);
        List<MessageResp> messageListResp = null;
        if (!CollectionUtils.isEmpty(messageList)){
            messageListResp = new ArrayList<>();
            MessageResp messageResp = null;
            for (CityMessage message : messageList){
                messageResp = this.getMessageResp(message);
                messageListResp.add(messageResp);
            }
        }
        return new PageInfo<>(messageListResp);
    }

    private CityMessage getMessage(MessageReq messageReq){
        PlayerResp getPlayerByName = playerService.getPlayerByName(messageReq.getPlayerNick());
        String playerId = null;
        if (getPlayerByName != null){
            playerId = getPlayerByName.getPlayerId();
        }
        PlayerResp getFriendByNick = playerService.getPlayerByNick(messageReq.getFriendNick());
        String friendId = null;
        if (getFriendByNick != null){
            friendId = getFriendByNick.getPlayerId();
        }

        CityMessage message = DataUtils.toJavaObject(messageReq,CityMessage.class);
        message.setPlayerId(playerId);
        message.setFriendId(friendId);
        return message;
    }

    private MessageResp getMessageResp(CityMessage message){
        PlayerResp player = playerService.getPlayerByPlayerId(message.getPlayerId());
        PlayerResp frien = playerService.getPlayerByPlayerId(message.getFriendId());
        MessageResp messageResp = DataUtils.toJavaObject(message,MessageResp.class);
        messageResp.setPlayerName(player.getPlayerName());
        messageResp.setPlayerNick(player.getPlayerNick());
        messageResp.setFriendName(frien.getPlayerName());
        messageResp.setFriendNick(frien.getPlayerNick());
        return messageResp;
    }


}
