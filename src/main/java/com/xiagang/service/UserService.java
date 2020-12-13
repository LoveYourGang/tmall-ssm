package com.xiagang.service;

import com.xiagang.bean.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    int registerUser(User user);
    boolean existName(String name);
}
