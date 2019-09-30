package com.dream.city.setting.dao;


import com.dream.city.setting.entity.RuleItem;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RuleItemMapper {
    int deleteByPrimaryKey(Integer itemId);

    int insert(RuleItem record);

    int insertSelective(RuleItem record);

    RuleItem selectByPrimaryKey(Integer itemId);

    int updateByPrimaryKeySelective(RuleItem record);

    int updateByPrimaryKey(RuleItem record);
}