package com.dream.city.setting.service.impl;

import com.dream.city.base.model.dto.UserRoleDto;
import com.dream.city.base.model.entity.UserRole;
import com.dream.city.base.model.mapper.UserRoleMapper;
import com.dream.city.setting.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Wvv on 2017/11/02.
 */
@Service
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public int insert(UserRole userRole){
        return userRoleMapper.insert(userRole);
    }

    @Override
    public int update(Map param){
        return userRoleMapper.update(param);
    }

    @Override
    public int delete(Integer userId){
        return userRoleMapper.deleteByUserId(userId);
    }

    @Override
    public List<UserRoleDto> getUserRoleList(List<Integer> userIdList){
        return userRoleMapper.getUserRoleList(userIdList);
    }
}
