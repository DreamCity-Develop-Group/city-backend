package com.dream.city.setting.service;


import com.dream.city.base.PageReq;
import com.dream.city.setting.entity.RuleItem;
import com.github.pagehelper.PageInfo;



/**
 * 规则项
 * @author
 */
public interface RuleItemService {

    Integer deleteRuleItem(Integer id);

    Integer updateRuleItem(RuleItem record);

    Integer insertRuleItem(RuleItem record);

    RuleItem getRuleItemById(Integer id);

    PageInfo<RuleItem> getRuleItemList(PageReq<RuleItem> record);


}