package com.dream.city.setting.enu;

public enum GameSettingType {

    bg("背景音效"),
    game("游戏音效");

    // 成员变量
    private String desc;

    GameSettingType(String desc){
        this.desc = desc;
    }


    public String getDesc() {
        return desc;
    }
}
