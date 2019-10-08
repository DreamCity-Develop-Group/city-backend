package com.dream.city.service.player;


import com.dream.city.base.model.entity.PlayerExt;

public interface PlayerExtService {

    Integer deletePlayerExtById(Long id);

    Integer insertPlayerExt(PlayerExt record);

    PlayerExt getPlayerExtById(Long id);

    Integer updatePlayerExtById(PlayerExt record);

    Integer updatePlayerExtByPlayerId(PlayerExt record);

}