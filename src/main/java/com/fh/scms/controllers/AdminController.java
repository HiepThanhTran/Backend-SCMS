package com.fh.scms.controllers;

import com.fh.scms.util.Utils;
import org.jetbrains.annotations.NotNull;
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
    public void commonAttributes(@NotNull Model model) {
        List<Map.Entry<String, String>> entities = Utils.generateMappingPojoClass();

        model.addAttribute("entities", entities);
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
