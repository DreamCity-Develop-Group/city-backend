package com.dream.city.service.player;


import com.dream.city.base.model.Page;
import com.dream.city.base.model.entity.LoginLog;
import com.dream.city.base.model.resp.LoginLogResp;
import com.github.pagehelper.PageInfo;


public interface LoginLogServcie {

    /**
     * 登录记录
     * @param record
     * @return
     */
    int insertLoginLog(LoginLog record);


    LoginLogResp getLoginLogById(Long id);

    PageInfo<LoginLogResp> getLoginLogList(Page record);
}
