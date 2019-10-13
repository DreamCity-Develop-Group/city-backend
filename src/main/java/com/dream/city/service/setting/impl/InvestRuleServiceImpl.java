package com.dream.city.service.setting.impl;

import com.dream.city.base.exception.BusinessException;
import com.dream.city.base.model.Page;
import com.dream.city.base.model.entity.InvestRule;
import com.dream.city.base.model.entity.RuleItem;
import com.dream.city.base.model.mapper.InvestRuleMapper;
import com.dream.city.base.model.req.RuleReq;
import com.dream.city.base.model.resp.RuleResp;
import com.dream.city.base.utils.DataUtils;
import com.dream.city.service.setting.InvestRuleService;
import com.dream.city.service.setting.RuleItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

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
    @Transactional(rollbackFor = Exception.class)
    public Integer insertInvestRule(RuleReq record) throws BusinessException {
        List<RuleItem> items = itemService.getRuleItemListByName(record.getItemName());
        InvestRule ruleReq = DataUtils.toJavaObject(record,InvestRule.class);
        if (!CollectionUtils.isEmpty(items)){
            ruleReq.setRuleItem(items.get(0).getItemId());
        } else {
            throw new BusinessException("根据名称[" + record.getItemName()+ "]找不到对于的规则项");
        }
        return ruleMapper.insertSelective(ruleReq);
    }

    @Override
    public RuleResp getInvestRuleById(Integer id) {
        InvestRule rule = ruleMapper.selectByPrimaryKey(id);
        RuleResp ruleResp = DataUtils.toJavaObject(rule,RuleResp.class);
        RuleItem item = itemService.getRuleItemById(rule.getRuleItem());
        if (item != null){
            ruleResp.setItemName(item.getItemName());
            ruleResp.setItemFlag(item.getItemFlag());
        }
        return ruleResp;
    }

    @Override
    public PageInfo<RuleResp> getInvestRuleList(Page record) {
        RuleReq ruleReq = DataUtils.toJavaObject(record.getCondition(),RuleReq.class);
        PageHelper.startPage(record.getPageNum(),record.getPageSize(),record.isCount());
        List<RuleResp> ruleList = ruleMapper.getInvestRuleList(ruleReq);
        return new PageInfo(ruleList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer updateInvestRuleById(RuleReq record) throws BusinessException {
        List<RuleItem> items = itemService.getRuleItemListByName(record.getItemName());
        InvestRule recordReq = DataUtils.toJavaObject(record,InvestRule.class);
        if (!CollectionUtils.isEmpty(items)){
            recordReq.setRuleItem(items.get(0).getItemId());
        } else {
            throw new BusinessException("根据名称[" + record.getItemName()+ "]找不到对于的规则项");
        }
        return ruleMapper.updateByPrimaryKeySelective(recordReq);
    }

    @Override
    public List<InvestRule> getRuleFlagList(InvestRule record) {
        return ruleMapper.getRuleFlagList(record);
    }
}
