package com.fh.scm.controllers;

import com.fh.scm.util.Utils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@ControllerAdvice
@RequestMapping(path = "/admin")
public class AdminController {

    @ModelAttribute
    public void commonAttributes(Model model) {
        List<String> entities = Utils.generateMappingPojoClass();

        model.addAttribute("entities", entities);
    }

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
