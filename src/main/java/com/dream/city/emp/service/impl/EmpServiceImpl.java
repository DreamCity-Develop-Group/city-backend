package com.dream.city.emp.service.impl;

import com.dream.city.emp.dao.EmpMapper;
import com.dream.city.emp.dto.EmpDto;
import com.dream.city.emp.entity.Emp;
import com.dream.city.emp.service.EmpService;
import com.dream.city.setting.dao.UserRoleMapper;
import com.dream.city.setting.entity.UserRole;
import com.dream.city.util.DataUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by Wvv on 2017/11/02.
 */
@Service
public class EmpServiceImpl implements EmpService {

    @Autowired
    private EmpMapper empMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public int insert(EmpDto empDto){

        //新建用户
        Emp emp = DataUtils.getData(empDto, Emp.class);
        empMapper.insertUser(emp);
        if(empDto.getRoleIdList() != null && empDto.getRoleIdList().size() > 0) {

            //添加用户角色关系
            for(Integer roleId : empDto.getRoleIdList()){
                UserRole userRole = new UserRole(empDto.getId(), roleId);
                userRoleMapper.insert(userRole);
            }
        }
        return emp.getId();
    }

    @Override
    public int update(Emp emp){
        return empMapper.updateById(emp);
    }

    @Override
    public List<Map> getList(Map param){
        return DataUtils.getDataArray(empMapper.getList(param), Map.class);
    }

    @Override
    public Integer getCount(Map param){
        return empMapper.getCount(param);
    }

    @Override
    public Emp getUserDetail(Integer id){
        return empMapper.getById(id);
    }

    @Override
    public int updateUser(EmpDto empDto){

        //1.更新用户
        Emp emp = DataUtils.getData(empDto, Emp.class);
        int count = empMapper.updateById(emp);
        if(empDto.getRoleIdList() != null && empDto.getRoleIdList().size() > 0) {

            //2.删除用户角色关系
            count += userRoleMapper.deleteByUserId(emp.getId());

            //3.添加用户角色关系
            for(Integer roleId : empDto.getRoleIdList()){
                UserRole userRole = new UserRole(empDto.getId(), roleId);
                count += userRoleMapper.insert(userRole);
            }
        }
        return count;
    }

    @Override
    public void deleteUser(Emp emp){

        //更新del_flag = 1
        empMapper.updateById(emp);
        //删除用户角色关系
        userRoleMapper.deleteByUserId(emp.getId());
    }

    /**
     * 初始化角色id
     * @param param
     */
    private List<String> getRoleIdList(Map param){
        if(null == param.get("roleIds") || StringUtils.isBlank(param.get("roleIds").toString())) {
            return new ArrayList<>();
        }
        String roleIds = param.get("roleIds").toString();
        return Arrays.asList(roleIds.split("\\$\\$"));
    }

    @Override
    public Emp findUserByName(String loginName){
        return this.empMapper.findUserByName(loginName);
    }
}
