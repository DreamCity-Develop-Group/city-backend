package com.dream.city.account.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author wvv
 */
@Data
public class PlayerAccount implements Serializable {
    /**  */
    private Integer accId;

    private String platformAccIds;

    /** 账户玩家 */
    private String accPlayerId;
    /**
     * 玩家账户地址
     */
    private String accAddr;

    /**
     * 玩家账户密码
     */
    private String accPass;

    /** 账户usdt额度 */
    private BigDecimal accUsdt;

    /** usdt可用金额 */
    private BigDecimal accUsdtAvailable;

    /** usdt冻结金额 */
    private BigDecimal accUsdtFreeze;

    /** 账户mt额度 */
    private BigDecimal accMt;

    /** mt可用金额 */
    private BigDecimal accMtAvailable;

    /** mt冻结金额 */
    private BigDecimal accMtFreeze;

    /** 积累总收入 */
    private BigDecimal accIncome;

    /**  */
    private Date createTime;

    /**  */
    private Date updateTime;


    public Integer getAccId() {
        return accId;
    }

    public void setAccId(Integer accId) {
        this.accId = accId;
    }

    public String getAccPlayerId() {
        return accPlayerId;
    }

    public void setAccPlayerId(String accPlayerId) {
        this.accPlayerId = accPlayerId;
    }

    public String getAccAddr() {
        return accAddr;
    }

    public void setAccAddr(String accAddr) {
        this.accAddr = accAddr;
    }

    public String getAccPass() {
        return accPass;
    }

    public void setAccPass(String accPass) {
        this.accPass = accPass;
    }

    public BigDecimal getAccUsdt() {
        return accUsdt;
    }

    public void setAccUsdt(BigDecimal accUsdt) {
        this.accUsdt = accUsdt;
    }

    public BigDecimal getAccUsdtAvailable() {
        return accUsdtAvailable;
    }

    public void setAccUsdtAvailable(BigDecimal accUsdtAvailable) {
        this.accUsdtAvailable = accUsdtAvailable;
    }

    public BigDecimal getAccUsdtFreeze() {
        return accUsdtFreeze;
    }

    public void setAccUsdtFreeze(BigDecimal accUsdtFreeze) {
        this.accUsdtFreeze = accUsdtFreeze;
    }

    public BigDecimal getAccMt() {
        return accMt;
    }

    public void setAccMt(BigDecimal accMt) {
        this.accMt = accMt;
    }

    public BigDecimal getAccMtAvailable() {
        return accMtAvailable;
    }

    public void setAccMtAvailable(BigDecimal accMtAvailable) {
        this.accMtAvailable = accMtAvailable;
    }

    public BigDecimal getAccMtFreeze() {
        return accMtFreeze;
    }

    public void setAccMtFreeze(BigDecimal accMtFreeze) {
        this.accMtFreeze = accMtFreeze;
    }

    public BigDecimal getAccIncome() {
        return accIncome;
    }

    public void setAccIncome(BigDecimal accIncome) {
        this.accIncome = accIncome;
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

    public String getPlatformAccIds() {
        return platformAccIds;
    }

    public void setPlatformAccIds(String platformAccIds) {
        this.platformAccIds = platformAccIds;
    }
}