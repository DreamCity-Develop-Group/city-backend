package com.dream.city.invest.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Order implements Serializable {
    /**  */
    private Integer orderId;

    /** 投资项目ID */
    private Integer orderInvestId;

    /** 玩家ID */
    private String orderPayerId;

    /** 投资金额 */
    private BigDecimal orderAmount;

    /** 状态 */
    private String orderState;

    /** 是否复投 */
    private int orderRepeat;

    /**  */
    private Date createTime;

    /**  */
    private Date updateTime;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getOrderInvestId() {
        return orderInvestId;
    }

    public void setOrderInvestId(Integer orderInvestId) {
        this.orderInvestId = orderInvestId;
    }

    public String getOrderPayerId() {
        return orderPayerId;
    }

    public void setOrderPayerId(String orderPayerId) {
        this.orderPayerId = orderPayerId == null ? null : orderPayerId.trim();
    }

    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }

    public int getOrderRepeat() {
        return orderRepeat;
    }

    public void setOrderRepeat(int orderRepeat) {
        this.orderRepeat = orderRepeat;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
    }
}