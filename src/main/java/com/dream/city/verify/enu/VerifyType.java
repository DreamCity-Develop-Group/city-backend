package com.dream.city.verify.enu;

public enum VerifyType {

    //提现
    WITHDRAW("提现"),

    //转账
    TRANSFER("转账"),

    //投资预约
    INVEST_SUBSCRIBE("投资预约"),

    //投资
    INVEST("投资");


    private String desc;

    VerifyType(String desc){
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }


}
