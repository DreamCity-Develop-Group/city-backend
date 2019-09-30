package com.dream.city.user.dao;

import com.dream.city.user.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {
    int deleteByUuid(String uuid);

    int insertUser(User user);

    User getById(Integer id);

    int updateById(User user);

    List<User> getList(Map param);

    Integer getCount(Map param);

    User findUserByName(String loginName);
}