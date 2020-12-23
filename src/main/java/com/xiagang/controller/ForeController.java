package com.xiagang.controller;

import com.xiagang.bean.User;
import com.xiagang.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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

    @RequestMapping("/home.do")
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

    @RequestMapping("/registerCheck.do")
    @ResponseBody
    public String registerCheck(String name) {
        String msg;
        int flag = userService.registerCheck(name);
        if(flag == -1) {
            msg = "<font color='red'>用户名不合法(不能为空或有空格)</font>";
        } else if(flag == 1) {
            msg = "<font color='red'>用户名已存在</font>";
        } else {
            msg = "<font color='green'>用户名可以使用</font>";
        }
        return msg;
    }

<<<<<<< HEAD
    @RequestMapping("/login.do")
=======
    @RequestMapping("login.do")
>>>>>>> 9f373a11234ebb9d0f8c25f5834b1589eeeccb91
    public ModelAndView login(HttpServletRequest request, String name, String password) {
        ModelAndView mv = new ModelAndView();
        HttpSession session = request.getSession();
        String msg, page;
        if(name != null && password != null) {
            boolean flag = userService.login(session, name, password);
            if(flag) {
                page = "redirect:home.do";
                mv.setViewName(page);
            } else {
                page = "login.jsp";
                msg = "账号或者密码错误";
                mv.addObject("msg", msg);
                mv.setViewName(page);
            }
        }
        return mv;
    }

    @RequestMapping("/ajaxLogin.do")
    @ResponseBody
    public String ajaxLogin(HttpServletRequest request, String name, String password) {
        HttpSession session = request.getSession();
        String msg;
        if(userService.loginCheck(session)) {
            msg = "success";
        } else {
            msg = "fail";
            if(userService.login(session, name, password))
                msg = "success";
        }
        return msg;
    }

    @RequestMapping("/checkLogin.do")
    @ResponseBody
    public String checkLogin(HttpServletRequest request) {
        if(userService.loginCheck(request.getSession())) {
            return "success";
        }
        return "fail";
    }

<<<<<<< HEAD
    @RequestMapping("/logout.do")
=======
    @RequestMapping("logout.do")
>>>>>>> 9f373a11234ebb9d0f8c25f5834b1589eeeccb91
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if(userService.loginCheck(session)) {
            userService.logout(session);
        }
        return "redirect:home.do";
    }

}
