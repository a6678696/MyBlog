package com.ledao.mapper;

import com.ledao.entity.User;

import java.util.Map;

/**
 * @author LeDao
 * @company
 * @create 2020-09-08 20:24
 */
public interface UserMapper {

    /**
     * 根据用户名查找用户
     *
     * @param userName
     * @return
     */
    User findByUserName(String userName);
}
