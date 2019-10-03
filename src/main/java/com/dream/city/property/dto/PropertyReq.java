package com.dream.city.property.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author
 */
@Data
public class PropertyReq implements Serializable {

    /** 标识 */
    private Integer inId;

    /** 名称 */
    private String inName;

    private String isValid;

    /** 投资结束时间 */
    private String inEnd;





}