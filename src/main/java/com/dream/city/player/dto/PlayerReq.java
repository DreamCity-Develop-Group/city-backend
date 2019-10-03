package com.dream.city.player.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class PlayerReq implements Serializable {

    private Long id;

    private String playerId;

    private String playerName;

    private String playerNick;

    private String playerInvite;

    private Integer playerLevel;

    private String isValid;

    private String createTime;


}