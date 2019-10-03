package com.dream.city.setting.dao;


import com.dream.city.setting.entity.InvestRule;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface InvestRuleMapper {

    int deleteByPrimaryKey(Integer ruleId);

    int insertSelective(InvestRule record);

    InvestRule selectByPrimaryKey(Integer ruleId);

    int updateByPrimaryKeySelective(InvestRule record);

}