package com.dream.city.player.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class PlayerResp implements Serializable {

    private Long id;

    private String playerId;

    private String playerName;

    private String playerNick;

    private String playerPass;

    private String playerTradePass;

    private String playerInvite;

    private Integer playerLevel;

    private String isValid;

    private String imgurl;

    private String createTime;

    private String updateTime;


}