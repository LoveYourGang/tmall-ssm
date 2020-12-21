package com.xiagang.service.impl;

import com.xiagang.bean.User;
import com.xiagang.dao.UserDao;
import com.xiagang.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpSession;

public class UserServiceImpl implements UserService {
    private UserDao userDao;
    @Autowired
    public UserServiceImpl (UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public int register(User user) {
        return userDao.insertUser(user);
    }

    public boolean isExist(String name) {
        User user = userDao.selectUserByName(name);
        if(user != null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String registerCheck(String name) {
        return null;
    }

    @Override
    public boolean login(String name, String password) {
        return false;
    }

    @Override
    public boolean loginAjax(String name, String password) {
        return false;
    }

    @Override
    public void logout(HttpSession session) {

    }

    @Override
    public boolean loginCheck(HttpSession session) {
        return false;
    }
}
