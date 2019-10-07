package com.dream.city.setting.service.impl;

import com.dream.city.base.Codes;
import com.dream.city.base.model.Page;
import com.dream.city.base.model.entity.InvestRule;
import com.dream.city.base.model.entity.RuleItem;
import com.dream.city.base.model.mapper.InvestRuleMapper;
import com.dream.city.base.model.req.RuleReq;
import com.dream.city.base.model.resp.RuleResp;
import com.dream.city.base.utils.DataUtils;
import com.dream.city.exception.OperationException;
import com.dream.city.setting.service.InvestRuleService;
import com.dream.city.setting.service.RuleItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author
 */
@Service
public class InvestRuleServiceImpl implements InvestRuleService {


    @Autowired
    InvestRuleMapper ruleMapper;
    @Autowired
    RuleItemService itemService;

    @Override
    public Integer deleteInvestRuleById(Integer id) {
        return ruleMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Integer insertInvestRule(InvestRule record) {
        return ruleMapper.insertSelective(record);
    }

    @Override
    public RuleResp getInvestRuleById(Integer id) {
        InvestRule rule = ruleMapper.selectByPrimaryKey(id);
        RuleResp ruleResp = DataUtils.getData(rule,RuleResp.class);
        RuleItem item = itemService.getRuleItemById(rule.getRuleItem());
        if (item != null){
            ruleResp.setItemName(item.getItemName());
        }
        return ruleResp;
    }

    @Override
    public PageInfo<RuleResp> getInvestRuleList(Page record) {
        RuleReq ruleReq = DataUtils.toJavaObject(record.getCondition(),RuleReq.class);
        PageHelper.startPage(record.getPageNum(),record.getPageSize(),record.isCount());
        List<InvestRule> ruleList = ruleMapper.getInvestRuleList(ruleReq);
        List<RuleResp> ruleListResp = new ArrayList<>();
        if (!CollectionUtils.isEmpty(ruleList)){
            RuleItem item = null;
            RuleResp ruleResp = null;
            for (InvestRule rule:ruleList){
                ruleResp = DataUtils.getData(rule,RuleResp.class);
                item = itemService.getRuleItemById(rule.getRuleItem());
                if (item != null){
                    ruleResp.setItemName(item.getItemName());
                }
                ruleListResp.add(ruleResp);
            }
        }
        return new PageInfo(ruleListResp);
    }

    @Override
    public Integer updateInvestRuleById(RuleReq record) throws OperationException {
        List<RuleItem> items = itemService.getRuleItemListByName(record.getItemName());
        InvestRule recordReq = DataUtils.toJavaObject(record,InvestRule.class);
        if (!CollectionUtils.isEmpty(items)){
            recordReq.setRuleItem(items.get(0).getItemId());
        } else {
            throw new OperationException(Codes.PARAM_ERROR.getCode(),"根据名称[" + record.getItemName()+ "]找不到对于的规则项");
        }
        return ruleMapper.updateByPrimaryKeySelective(recordReq);
    }
}
