package com.dream.city.setting.dao;


import com.dream.city.setting.entity.RuleItem;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 规则项
 * @author
 */
@Mapper
public interface RuleItemMapper {

    Integer deleteByPrimaryKey(Integer itemId);

    Integer updateByPrimaryKeySelective(RuleItem record);

    Integer insertSelective(RuleItem record);

    RuleItem selectByPrimaryKey(Integer itemId);

    List<RuleItem> getRuleItemList(RuleItem record);


}