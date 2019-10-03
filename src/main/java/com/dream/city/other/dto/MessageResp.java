package com.dream.city.other.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class MessageResp implements Serializable {
    /**  */
    private Long id;

    /**  */
    private String playerId;
    private String playerName;
    private String playerNick;

    /**  */
    private String friendId;
    private String friendName;
    private String friendNick;

    private String content;

    private Integer haveRead;

    private String sendTime;

    /**  */
    private String createTime;

    /**  */
    private String updateTime;


}