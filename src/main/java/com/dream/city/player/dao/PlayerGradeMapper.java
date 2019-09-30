package com.dream.city.player.dao;


import com.dream.city.player.entity.PlayerGrade;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PlayerGradeMapper {

    int deleteByPrimaryKey(Long id);

    int insertSelective(PlayerGrade record);

    PlayerGrade selectByPrimaryKey(Long id);

    PlayerGrade getPlayerGradeByPlayerId(PlayerGrade record);

    int updateByPlayerId(PlayerGrade record);

}