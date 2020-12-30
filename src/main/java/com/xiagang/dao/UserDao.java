package com.xiagang.dao;

import com.xiagang.bean.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserDao {
    int insertUser(User user);

    User selectUserById(Integer id);
    User selectUserByName(String name);
    User selectUserByPassword(@Param("name") String name, @Param("password") String password);
    List<User> selectAllUsers();

    int updateUser(User user);
}
