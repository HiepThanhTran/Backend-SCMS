package com.fh.scm.controllers;

import com.fh.scm.util.Utils;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
@ControllerAdvice
@RequestMapping(path = "/admin")
public class AdminController {

    @ModelAttribute
    public void commonAttributes(Model model) {
        Map<String, String> entities = Utils.generateMappingPojoClass();

        model.addAttribute("entities", entities);
    }

    @GetMapping
    public String dashBoard() {
        return "dashboard";
    }

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

    @GetMapping(path = "/statistics")
    public String statisticsReport() {
        return "statistics";
    }

    @GetMapping(path = "/analytics")
    public String analyticsReport() {
        return "analytics";
    }
}
