package com.fh.scms.controllers.admin;

import com.fh.scms.dto.MessageResponse;
import com.fh.scms.enums.UserRole;
import com.fh.scms.pojo.User;
import com.fh.scms.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
@RequestMapping(path = "/admin/users", produces = "application/json; charset=UTF-8")
public class UserController {

    private final UserService userService;

    @GetMapping
    public String listUser(Model model, @RequestParam(required = false, defaultValue = "") Map<String, String> params) {
        model.addAttribute("users", this.userService.getAll(params));

        return "users";
    }

    @RequestMapping(path = "/edit/{userId}", method = {RequestMethod.GET, RequestMethod.POST})
    public String editUser(HttpServletRequest request, Model model, @PathVariable(value = "userId") Long id,
                           @ModelAttribute(value = "user") @Valid User user, BindingResult bindingResult) {
        model.addAttribute("userRoles", UserRole.getAllDisplayNames());

        if (request.getMethod().equals("POST")) {
            if (bindingResult.hasErrors()) {
                List<MessageResponse> errors = MessageResponse.fromBindingResult(bindingResult);
                model.addAttribute("errors", errors);

                return "edit_user";
            }

            this.userService.update(user);

            return "redirect:/admin/users";
        }

        model.addAttribute("user", this.userService.get(id));

        return "edit_user";
    }

    @DeleteMapping(path = "/delete/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String deleteUser(@PathVariable(value = "userId") Long id) {
        this.userService.delete(id);

        return "redirect:/admin/users";
    }
}
