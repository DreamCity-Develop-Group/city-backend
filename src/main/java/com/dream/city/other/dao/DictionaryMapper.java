package com.dream.city.other.dao;

import com.dream.city.other.entity.Dictionary;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DictionaryMapper {


    Integer deleteByPrimaryKey(Integer id);

    Integer insertSelective(Dictionary record);

    Dictionary selectByPrimaryKey(Integer id);

    List<Dictionary> selectByPrimaryKey(Dictionary id);

    Integer updateByPrimaryKeySelective(Dictionary record);

}