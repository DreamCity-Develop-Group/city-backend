package com.dream.city.player.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class PlayerGameSettingResp implements Serializable {

    private Long id;

    private String playerId;
    private String playerNick;

    private String type;

    private String val;

    private String status;


}