package com.dream.city.setting.service.impl;

import com.dream.city.base.PageReq;
import com.dream.city.setting.dao.RuleItemMapper;
import com.dream.city.setting.entity.RuleItem;
import com.dream.city.setting.service.RuleItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RuleItemServiceImpl implements RuleItemService {


    @Autowired
    RuleItemMapper ruleItemMapper;

    @Override
    public Integer deleteRuleItem(Integer id) {
        return ruleItemMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Integer updateRuleItem(RuleItem record) {
        return ruleItemMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public Integer insertRuleItem(RuleItem record) {
        return ruleItemMapper.insertSelective(record);
    }

    @Override
    public RuleItem getRuleItemById(Integer id) {
        return ruleItemMapper.selectByPrimaryKey(id);
    }

    @Override
    public PageInfo<RuleItem> getRuleItemList(PageReq<RuleItem> record) {
        PageHelper.startPage(record.getPageNum(),record.getPageSize(),record.isCount());
        return new PageInfo<>(ruleItemMapper.getRuleItemList(record.getCondition()));
    }
}