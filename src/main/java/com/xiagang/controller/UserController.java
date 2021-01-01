package com.xiagang.controller;

import com.xiagang.bean.User;
import com.xiagang.service.UserService;
import com.xiagang.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/list.do")
    public ModelAndView list(@RequestParam(value = "page.start",defaultValue = "0") Integer start) {
        ModelAndView mv = new ModelAndView("admin/listUser.jsp");
        Page page = new Page(start, 10);
        List<User> us = userService.getAllUsers();
        page.setTotal(us.size());
        mv.addObject("us", us);
        mv.addObject("page", page);
        mv.addObject("path", "user/list.do");
        return mv;
    }
}
