package com.xiagang.controller;

import com.xiagang.bean.User;
import com.xiagang.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller("/fore")
public class ForeController {
    private UserService userService;

    @Autowired
    public ForeController(UserService userService) {
        this.userService = userService;
    }

    public ModelAndView home() {
        ModelAndView mv = new ModelAndView();
        return mv;
    }

    @RequestMapping("/register.do")
    public ModelAndView register(String name, String password) {
        ModelAndView mv = new ModelAndView();
        String page = "register.jsp";
        if(password == null || password.length() < 6) {
            String msg = "至少输入6位数密码";
            page = "forward:register.jsp";
            mv.addObject("msg", msg);
            mv.setViewName(page);
            return mv;
        }
        if(name != null && userService.registerCheck(name) == 0) {
            User user = new User(name, password);
            int res = userService.register(user);
            if(res > 0) {
                page = "redirect:registerSuccess.jsp";
            } else {
                page = "redirect:register.jsp";
            }
        }
        mv.setViewName(page);
        return mv;
    }

    @RequestMapping("login.do")
    public ModelAndView login(HttpServletRequest request, String name, String password) {
        ModelAndView mv = new ModelAndView();
        HttpSession session = request.getSession();
        String msg, page;
        if(name != null && password != null) {
            boolean flag = userService.login(session, name, password);
            if(flag) {
                page = "home.jsp";
                mv.setViewName(page);
            } else {
                page = "register.jsp";
                msg = "账号或者密码错误";
                mv.addObject("msg", msg);
                mv.setViewName(page);
            }
        }
        return mv;
    }

    @RequestMapping("logout.do")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if(userService.loginCheck(session)) {
            userService.logout(session);
        }
        return "index.jsp";
    }

}
