package com.xiagang.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

@Controller("/fore")
public class ForeController {
    public ModelAndView home() {
        ModelAndView mv = new ModelAndView();
        return mv;
    }
}
