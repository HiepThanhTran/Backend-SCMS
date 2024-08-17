package com.fh.scm.controllers;

import com.fh.scm.components.JWTService;
import com.fh.scm.dto.user.UserLoginRequest;
import com.fh.scm.dto.user.UserRegisterRequest;
import com.fh.scm.dto.user.UserResponse;
import com.fh.scm.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping(path = "/api/user", produces = "application/json; charset=UTF-8")
public class APIUserController {

    @Autowired
    private JWTService jwtService;
    @Autowired
    private UserService userService;

    @PostMapping(path = "/login")
    public ResponseEntity<?> authenticateUser(@RequestBody UserLoginRequest userLoginRequest) {
        if (this.userService.auth(userLoginRequest.getUsername(), userLoginRequest.getPassword())) {
            String token = this.jwtService.generateTokenLogin(userLoginRequest.getUsername());

            return ResponseEntity.ok(token);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Sai tên đăng nhập hoặc mật khẩu");
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@ModelAttribute @Valid UserRegisterRequest userRegisterRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult
                    .getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }

        UserResponse userResponse = this.userService.register(userRegisterRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }

    @GetMapping("/profile")
    public ResponseEntity<UserResponse> details(Principal principal) {
        UserResponse userResponse = this.userService.profile(principal.getName());

        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }
}
