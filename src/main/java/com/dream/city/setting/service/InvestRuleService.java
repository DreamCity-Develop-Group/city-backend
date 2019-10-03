package com.dream.city.setting.service;


import com.dream.city.base.PageReq;
import com.dream.city.exception.OperationException;
import com.dream.city.setting.dto.RuleReq;
import com.dream.city.setting.dto.RuleResp;
import com.dream.city.setting.entity.InvestRule;
import com.github.pagehelper.PageInfo;


public interface InvestRuleService {

    Integer deleteInvestRuleById(Integer id);

    Integer insertInvestRule(InvestRule record);

    RuleResp getInvestRuleById(Integer id);

    PageInfo getInvestRuleList(PageReq<RuleReq> record);

    Integer updateInvestRuleById(RuleReq record) throws OperationException;

}