package com.dream.city.player.service;


import com.dream.city.player.entity.LoginLog;

public interface LoginLogServcie {

    /**
     * 登录记录
     * @param record
     * @return
     */
    int insertLoginLog(LoginLog record);
}
