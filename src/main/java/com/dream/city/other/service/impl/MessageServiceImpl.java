package com.dream.city.other.service.impl;

import com.alibaba.fastjson.JSON;
import com.dream.city.base.PageReq;
import com.dream.city.other.dao.MessageMapper;
import com.dream.city.other.dto.MessageReq;
import com.dream.city.other.dto.MessageResp;
import com.dream.city.other.entity.CityMessage;
import com.dream.city.other.service.MessageService;
import com.dream.city.player.entity.Player;
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
    public PageInfo<MessageResp> getCityMessageList(PageReq<MessageReq> record) {
        PageHelper.startPage(record.getPageNum(), record.getPageSize(),record.isCount());
        CityMessage getCityMessageListReq = this.getMessage(record.getCondition());
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
        Player getPlayerByName = playerService.getPlayerByName(messageReq.getPlayerNick(),null);
        String playerId = null;
        if (getPlayerByName != null){
            playerId = getPlayerByName.getPlayerId();
        }
        Player getPlayerNick = playerService.getPlayerByName(null,messageReq.getPlayerNick());
        if (getPlayerNick != null){
            playerId = getPlayerNick.getPlayerId();
        }

        Player getFriendByName = playerService.getPlayerByName(messageReq.getFriendNick(),null);
        String friendId = null;
        if (getFriendByName != null){
            friendId = getFriendByName.getPlayerId();
        }
        Player getFriendNick = playerService.getPlayerByName(null, messageReq.getFriendNick());
        if (getFriendNick != null){
            friendId = getFriendNick.getPlayerId();
        }

        CityMessage message = JSON.toJavaObject(JSON.parseObject(JSON.toJSONString(messageReq)),CityMessage.class);
        message.setPlayerId(playerId);
        message.setFriendId(friendId);
        return message;
    }

    private MessageResp getMessageResp(CityMessage message){
        Player player = playerService.getPlayerById(message.getPlayerId());
        Player frien = playerService.getPlayerById(message.getFriendId());
        MessageResp messageResp = JSON.toJavaObject(JSON.parseObject(JSON.toJSONString(message)),MessageResp.class);
        messageResp.setPlayerName(player.getPlayerName());
        messageResp.setPlayerNick(player.getPlayerNick());
        messageResp.setFriendName(frien.getPlayerName());
        messageResp.setFriendNick(frien.getPlayerNick());
        return messageResp;
    }


}
