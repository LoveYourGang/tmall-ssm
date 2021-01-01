package com.xiagang.service.impl;

import com.xiagang.bean.User;
import com.xiagang.dao.OrderItemDao;
import com.xiagang.dao.UserDao;
import com.xiagang.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {
    private UserDao userDao;
    private OrderItemDao orderItemDao;
    @Autowired
    public UserServiceImpl(UserDao userDao, OrderItemDao orderItemDao) {
        this.userDao = userDao;
        this.orderItemDao = orderItemDao;
    }

    @Override
    public int register(User user) {
        return userDao.insertUser(user);
    }

    @Override
    public int registerCheck(String name) {
        if(name == null || name.isEmpty() || name.contains(" "))
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
    public List<User> getAllUsers() {
        return userDao.selectAllUsers();
    }

    @Override
    public boolean login(HttpSession session, String name, String password) {
        User user = userDao.selectUserByPassword(name, password);
        if(user != null) {
            session.setAttribute("user", user);
            int cartTotalItemNumber = orderItemDao.selectUserCartCount(user);
            session.setAttribute("cartTotalItemNumber", cartTotalItemNumber);
            return true;
        }
        return false;
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
