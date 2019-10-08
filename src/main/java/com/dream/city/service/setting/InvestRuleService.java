package com.dream.city.service.setting;


import com.dream.city.base.model.Page;
import com.dream.city.base.model.entity.InvestRule;
import com.dream.city.base.model.req.RuleReq;
import com.dream.city.base.model.resp.RuleResp;
import com.dream.city.exception.OperationException;
import com.github.pagehelper.PageInfo;


public interface InvestRuleService {

    Integer deleteInvestRuleById(Integer id);

    Integer insertInvestRule(InvestRule record);

    RuleResp getInvestRuleById(Integer id);

    PageInfo getInvestRuleList(Page record);

    Integer updateInvestRuleById(RuleReq record) throws OperationException;

}