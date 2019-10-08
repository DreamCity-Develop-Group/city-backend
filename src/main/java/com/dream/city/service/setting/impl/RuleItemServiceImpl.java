package com.dream.city.service.setting.impl;

import com.dream.city.base.model.Page;
import com.dream.city.base.model.entity.RuleItem;
import com.dream.city.base.model.mapper.RuleItemMapper;
import com.dream.city.base.utils.DataUtils;
import com.dream.city.service.setting.RuleItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
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
    public List<RuleItem> getRuleItemListByName(String itemName) {
        if (StringUtils.isNotBlank(itemName)){
            return null;
        }
        RuleItem record = new RuleItem();
        record.setItemName(itemName);
        return ruleItemMapper.getRuleItemList(record);
    }

    @Override
    public PageInfo<RuleItem> getRuleItemList(Page record) {
        RuleItem item = DataUtils.toJavaObject(record.getCondition(),RuleItem.class);
        PageHelper.startPage(record.getPageNum(),record.getPageSize(),record.isCount());
        return new PageInfo<>(ruleItemMapper.getRuleItemList(item));
    }
}
