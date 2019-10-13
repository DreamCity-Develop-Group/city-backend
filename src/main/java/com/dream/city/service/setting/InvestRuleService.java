package com.dream.city.service.setting;


import com.dream.city.base.exception.BusinessException;
import com.dream.city.base.model.Page;
import com.dream.city.base.model.entity.InvestRule;
import com.dream.city.base.model.req.RuleReq;
import com.dream.city.base.model.resp.RuleResp;
import com.github.pagehelper.PageInfo;

import java.util.List;


public interface InvestRuleService {

    Integer deleteInvestRuleById(Integer id);

    Integer insertInvestRule(RuleReq record) throws BusinessException;

    RuleResp getInvestRuleById(Integer id);

    PageInfo getInvestRuleList(Page record);

    Integer updateInvestRuleById(RuleReq record) throws BusinessException;

    List<InvestRule> getRuleFlagList(InvestRule record);

}