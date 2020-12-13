package com.xiagang.controller;

import com.xiagang.bean.User;
import com.xiagang.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller("/fore")
public class ForeController {
    @Autowired
    private UserService userService;

    public ModelAndView home() {
        ModelAndView mv = new ModelAndView();
        return mv;
    }

    @RequestMapping("/register.do")
    public ModelAndView register(User user) {
        ModelAndView mv = new ModelAndView();
        String msg;
        int res = userService.registerUser(user);
        if(res == 0) {
            msg = "注册信息有误，注册失败请重试。";
            mv.setViewName("register.jsp");
        } else {
            msg = "恭喜你注册成功！";
            mv.setViewName("registerSuccess.jsp");
        }
        mv.addObject("msg", msg);
        return mv;
    }
}
