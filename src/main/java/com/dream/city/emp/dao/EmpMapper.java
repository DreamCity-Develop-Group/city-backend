package com.dream.city.emp.dao;

import com.dream.city.emp.entity.Emp;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface EmpMapper {
    int deleteByUuid(String uuid);

    int insertUser(Emp emp);

    Emp getById(Integer id);

    int updateById(Emp emp);

    List<Emp> getList(Map param);

    Integer getCount(Map param);

    Emp findUserByName(String loginName);
}