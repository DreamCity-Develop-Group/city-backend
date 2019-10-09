package com.dream.city.service.player.impl;

import com.dream.city.base.model.Page;
import com.dream.city.base.model.entity.LoginLog;
import com.dream.city.base.model.mapper.LoginLogMapper;
import com.dream.city.base.model.req.LoginLogReq;
import com.dream.city.base.model.resp.LoginLogResp;
import com.dream.city.base.utils.DataUtils;
import com.dream.city.service.player.LoginLogServcie;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class LoginLogServcieImpl implements LoginLogServcie {

    private static final Logger logger = LoggerFactory.getLogger(LoginLogServcieImpl.class);

    @Autowired
    LoginLogMapper loginLogMapper;

    @Override
    public int insertLoginLog(LoginLog record) {
        int i = 0;
        try {
            record.setCreateTime(new Date());
            i = loginLogMapper.insertSelective(record);
        }catch (Exception e){
            i = 0;
            logger.error("新增登录日志异常!s",e);
        }
        return i;
    }

    @Override
    public LoginLogResp getLoginLogById(Long id) {
        return loginLogMapper.selectByPrimaryKey(id);
    }

    @Override
    public PageInfo<LoginLogResp> getLoginLogList(Page record) {
        LoginLogReq logReq = DataUtils.toJavaObject(record.getCondition(), LoginLogReq.class);
        PageHelper.startPage(record.getPageNum(),record.getPageSize());
        List<LoginLogResp> logList = loginLogMapper.getLoginLogList(logReq);
        return new PageInfo<>(logList);
    }



}
