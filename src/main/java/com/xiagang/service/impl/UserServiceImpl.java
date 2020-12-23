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

    @Override
    public int registerCheck(String name) {
<<<<<<< HEAD
        if(name == null || name.isEmpty() || name.contains(" "))
=======
        if(name == null || name.length() < 2 || name.contains(" "))
>>>>>>> 9f373a11234ebb9d0f8c25f5834b1589eeeccb91
            return -1;
        User user = userDao.selectUserByName(name);
        int flag;
        if(user != null) {
            flag = 1;
        } else {
            flag = 0;
        }
        return flag;
    }

    @Override
    public boolean login(HttpSession session, String name, String password) {
        User user = userDao.selectUserByPassword(name, password);
        if(user != null) {
            session.setAttribute("user", user);
            return true;
        }
        return false;
<<<<<<< HEAD
=======
    }

    @Override
    public String loginAjax(HttpSession session, String name, String password) {
        return null;
>>>>>>> 9f373a11234ebb9d0f8c25f5834b1589eeeccb91
    }

    @Override
    public void logout(HttpSession session) {
        session.removeAttribute("user");
    }

    @Override
    public boolean loginCheck(HttpSession session) {
        User user = (User) session.getAttribute("user");
        return user != null;
    }
}
