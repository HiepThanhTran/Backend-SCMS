package com.fh.scms.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/admin")
public class AdminController {

//    @GetMapping("/login")
//    public String showLoginForm(Model model) {
//
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
//            return "login";
//        }
//
//        return "redirect:/";
//    }

    @GetMapping
    public String dashBoard() {
        return "dashboard";
    }

    @GetMapping(path = "/statistics")
    public String statisticsReport() {
        return "statistics";
    }

    @GetMapping(path = "/analytics")
    public String analyticsReport() {
        return "analytics";
    }
}
