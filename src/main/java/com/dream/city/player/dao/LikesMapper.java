package com.dream.city.player.dao;


import com.dream.city.player.entity.Likes;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LikesMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Likes record);

    int insertSelective(Likes record);

    Likes selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Likes record);

    int updateByPrimaryKey(Likes record);
}