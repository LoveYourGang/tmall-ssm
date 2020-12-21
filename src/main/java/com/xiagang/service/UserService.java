package com.xiagang.service;

import com.xiagang.bean.User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public interface UserService {
    int register(User user);
    String registerCheck(String name);
    boolean isExist(String name);

    boolean login(String name, String password);
    boolean loginAjax(String name, String password);
    void logout(HttpSession session);
    boolean loginCheck(HttpSession session);
}
