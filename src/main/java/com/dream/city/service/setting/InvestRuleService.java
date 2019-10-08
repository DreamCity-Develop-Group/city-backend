package com.dream.city.service.setting;


import com.dream.city.base.model.Page;
import com.dream.city.base.model.entity.InvestRule;
import com.dream.city.base.model.req.RuleReq;
import com.dream.city.base.model.resp.RuleResp;
import com.dream.city.exception.OperationException;
import com.github.pagehelper.PageInfo;

import java.util.List;


public interface InvestRuleService {

    Integer deleteInvestRuleById(Integer id);

    Integer insertInvestRule(RuleReq record) throws OperationException ;

    RuleResp getInvestRuleById(Integer id);

    PageInfo getInvestRuleList(Page record);

    Integer updateInvestRuleById(RuleReq record) throws OperationException;

    List<InvestRule> getRuleFlagList(InvestRule record);

}