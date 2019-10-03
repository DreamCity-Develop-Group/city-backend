package com.dream.city.setting.dao;


import com.dream.city.setting.dto.RuleReq;
import com.dream.city.setting.entity.InvestRule;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface InvestRuleMapper {

    Integer deleteByPrimaryKey(Integer ruleId);

    Integer insertSelective(InvestRule record);

    InvestRule selectByPrimaryKey(Integer ruleId);

    List<InvestRule> getInvestRuleList(RuleReq record);

    Integer updateByPrimaryKeySelective(InvestRule record);

}