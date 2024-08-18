package com.fh.scm.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@ControllerAdvice
public class HomeController {

    @RequestMapping(path = "/")
    public String index(Model model, @RequestParam Map<String, String> params) {
        return "index";
    }
}
