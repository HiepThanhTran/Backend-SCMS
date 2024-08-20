package com.fh.scm.controllers;

import com.fh.scm.dto.ResponseMessage;
import com.fh.scm.pojo.User;
import com.fh.scm.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/admin/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    public String listUser(Model model, @RequestParam(required = false, defaultValue = "") Map<String, String> params) {
        model.addAttribute("users", userService.getAll(params));

        return "users";
    }

    @GetMapping(path = "/{userId}")
    public String retrieveUser(@PathVariable(value = "userId") Long id, Model model) {
        model.addAttribute("user", userService.get(id));

        return "user";
    }

    @RequestMapping(path = "/add", method = {RequestMethod.GET, RequestMethod.POST})
    public String addUser(HttpServletRequest request, Model model, @ModelAttribute(value = "user") @Valid User user,
                          BindingResult bindingResult) {
        if (request.getMethod().equals("POST")) {
            if (bindingResult.hasErrors()) {
                List<ResponseMessage> errors = ResponseMessage.fromBindingResult(bindingResult);
                model.addAttribute("errors", errors);

                return "add_user";
            }

            userService.insert(user);

            return "redirect:/admin/users";
        }

        return "add_user";
    }

    @RequestMapping(path = "/edit/{userId}", method = {RequestMethod.GET, RequestMethod.PATCH})
    public String editUser(HttpServletRequest request, Model model, @PathVariable(value = "userId") Long id,
                           @ModelAttribute(value = "user") @Valid User user, BindingResult bindingResult) {
        if (request.getMethod().equals("PATCH")) {
            if (bindingResult.hasErrors()) {
                List<ResponseMessage> errors = ResponseMessage.fromBindingResult(bindingResult);
                model.addAttribute("errors", errors);

                return "edit_user";
            }

            userService.update(user);

            return "redirect:/admin/users";
        }

        model.addAttribute("user", userService.get(id));

        return "edit_user";
    }

    @DeleteMapping(path = "/delete/{userId}")
    public String deleteUser(@PathVariable(value = "userId") Long id) {
        userService.delete(id);

        return "redirect:/admin/users";
    }

    @DeleteMapping(path = "/hide/{userId}")
    public String hideUser(@PathVariable(value = "userId") Long id) {
        userService.softDelete(id);

        return "redirect:/admin/users";
    }
}
