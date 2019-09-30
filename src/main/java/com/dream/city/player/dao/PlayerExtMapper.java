package com.dream.city.player.dao;


import com.dream.city.player.entity.PlayerExt;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PlayerExtMapper {

    Integer deleteByPrimaryKey(Long id);

    Integer insertSelective(PlayerExt record);

    PlayerExt selectByPrimaryKey(Long id);

    Integer updateByPrimaryKeySelective(PlayerExt record);

    Integer updateByPlayerIdSelective(PlayerExt record);

}