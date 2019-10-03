package com.dream.city.setting.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class RuleItemReq implements Serializable {
    /**  */
    private Integer itemId;

    /** 规则项目名称 */
    private String itemName;

    /** 规则项目描述 */
    private String itemDesc;

    /** 可用状态 */
    private Byte itemState;


}