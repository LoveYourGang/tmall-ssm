package com.xiagang.service;

import com.xiagang.bean.User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

public interface UserService {
    int register(User user);
    int registerCheck(String name);

    boolean login(HttpSession session, String name, String password);
    void logout(HttpSession session);
    boolean loginCheck(HttpSession session);
}
