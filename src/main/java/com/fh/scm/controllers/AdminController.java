package com.fh.scm.controllers;

import com.fh.scm.services.UserService;
import com.fh.scm.util.ClassScanner;
import com.fh.scm.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

@Controller
@ControllerAdvice
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @ModelAttribute
    public void commonAttributes(Model model) {
        Set<Class<?>> classes = ClassScanner.getClassesInPackage("com.fh.scm.pojo");
        Map<String, String> entities = Utils.generateMappingPojoClass(classes);

        model.addAttribute("entities", entities);
    }

    @GetMapping("/")
    public String dashBoard() {
        return "dashboard";
    }

    @GetMapping("/users")
    public String listUsers(Model model, @RequestParam Map<String, String> params) {
        model.addAttribute("users", this.userService.getAll(params));

        return "users";
    }
}
