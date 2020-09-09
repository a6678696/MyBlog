package com.ledao.service;

import com.ledao.entity.User;

/**
 * @author LeDao
 * @company
 * @create 2020-09-08 20:25
 */
public interface UserService {

    /**
     * 根据用户名查找用户
     *
     * @param userName
     * @return
     */
    User findByUserName(String userName);
}
