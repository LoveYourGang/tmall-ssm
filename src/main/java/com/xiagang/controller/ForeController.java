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
    public ModelAndView register(String name, String password) {
        ModelAndView mv = new ModelAndView();
        String msg, page;
        if(password == null || password.length() < 6) {
            msg = "至少输入6位数密码";
            page = "forward:register.jsp";
            mv.addObject("msg", msg);
            mv.setViewName(page);
            return mv;
        }
        if(name != null && userService.isExist(name)) {
            User user = new User(name, password);
            int res = userService.register(user);
            if(res > 0) {
                page = "redirect:registerSuccess.jsp";
            } else {
                page = "redirect:register.jsp";
            }
            mv.setViewName(page);
        }
        return mv;
    }


}
