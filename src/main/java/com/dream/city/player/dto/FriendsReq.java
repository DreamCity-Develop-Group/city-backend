package com.dream.city.player.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class FriendsReq implements Serializable {

    private Long id;

    private String playerId;
    private String playerName;
    private String playerNick;

    private String friendId;
    private String friendName;
    private String friendNick;

    private Integer agree;

    private String invite;

}