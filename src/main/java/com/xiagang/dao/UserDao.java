package com.xiagang.dao;

import com.xiagang.bean.User;

import java.util.List;

public interface UserDao {
    int insertUser(User user);

    User selectUserById(Integer id);
    User selectUserByName(String name);
    User selectUserByPassword(String name, String password);
    List<User> selectAllUsers();

    int updateUser(User user);
}
