package com.xiagang.interceptor;

import com.xiagang.bean.User;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class LoginInterceptor implements HandlerInterceptor {
    private List<String> notAuth = new ArrayList<>();

    {
        notAuth.add("/home.do");
        notAuth.add("/register.do");
        notAuth.add("/registerCheck.do");
        notAuth.add("/login.do");
        notAuth.add("/ajaxLogin.do");
        notAuth.add("/checkLogin.do");
        notAuth.add("/product.do");
        notAuth.add("/category.do");
        notAuth.add("/search.do");
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();
        String elem = uri.substring(uri.lastIndexOf('/'));
        if(notAuth.contains(elem))
            return true;
        User user = (User) request.getSession().getAttribute("user");
        if(user == null) {
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return false;
        }
        return true;
    }
}
