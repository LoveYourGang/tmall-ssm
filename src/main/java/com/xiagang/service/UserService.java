package com.xiagang.service;

import com.xiagang.bean.User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public interface UserService {
    int register(User user);
    int registerCheck(String name);

    boolean login(HttpSession session, String name, String password);
<<<<<<< HEAD
=======
    String loginAjax(HttpSession session, String name, String password);
>>>>>>> 9f373a11234ebb9d0f8c25f5834b1589eeeccb91
    void logout(HttpSession session);
    boolean loginCheck(HttpSession session);
}
