package com.dream.city.emp.service;

import com.dream.city.emp.dto.EmpDto;
import com.dream.city.emp.entity.Emp;

import java.util.List;
import java.util.Map;

/**
 * 职员
 * Created by Wvv on 2017/11/02.
 */
public interface EmpService {

    /**
     * 创建用户
     */
    int insert(EmpDto empDto);

    /**
     * 更新用户
     */
    int update(Emp emp);

    /**
     * 获取用户列表
     */
    List<Map> getList(Map param);

    /**
     * 获取用户数量
     */
    Integer getCount(Map param);

    /**
     * 获取用户详情
     */
    Emp getUserDetail(Integer id);

    /**
     * 更新用户相关信息
     */
    int updateUser(EmpDto empDto);

    /**
     * 删除用户相关信息（del_flag更新为1）
     */
    void deleteUser(Emp emp);

    /**
     * 根据用户名查找用户
     */
    Emp findUserByName(String loginName);
}
