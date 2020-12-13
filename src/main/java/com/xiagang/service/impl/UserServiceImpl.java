package com.xiagang.service.impl;

import com.xiagang.bean.User;
import com.xiagang.dao.UserDao;
import com.xiagang.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Override
    public int registerUser(User user) {
        String name = user.getName();
        if(name.length() > 20 || existName(name))
            return 0;

        return userDao.insertUser(user);
    }

    @Override
    public boolean existName(String name) {
        User user = userDao.selectUserByName(name);
        return user != null;
    }
}
