package com.dream.city.setting.service;


import com.dream.city.base.model.Page;
import com.dream.city.base.model.entity.RuleItem;
import com.github.pagehelper.PageInfo;

import java.util.List;


/**
 * 规则项
 * @author
 */
public interface RuleItemService {

    Integer deleteRuleItem(Integer id);

    Integer updateRuleItem(RuleItem record);

    Integer insertRuleItem(RuleItem record);

    RuleItem getRuleItemById(Integer id);

    List<RuleItem> getRuleItemListByName(String itemName);

    PageInfo<RuleItem> getRuleItemList(Page record);


}