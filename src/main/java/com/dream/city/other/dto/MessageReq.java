package com.dream.city.other.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class MessageReq implements Serializable {
    /**  */
    private Long id;

    /**  */
    private String playerId;
    private String playerNick;

    /**  */
    private String friendId;
    private String friendNick;

    private String content;

    private Integer haveRead;

    private String sendTime;

}