package com.xiagang.dao;

import com.xiagang.bean.User;

public interface UserDao {
    int insertUser(User user);

    User selectUserById(Integer id);
    User selectUserByName(String name);

    int updateUser(User user);
}
