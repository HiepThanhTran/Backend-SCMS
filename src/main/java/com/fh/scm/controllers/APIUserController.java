package com.fh.scm.controllers;

import com.fh.scm.components.JWTService;
import com.fh.scm.dto.api.user.UserRequestLogin;
import com.fh.scm.dto.api.user.UserRequestRegister;
import com.fh.scm.dto.api.user.UserRequestUpdate;
import com.fh.scm.dto.api.user.UserResponse;
import com.fh.scm.dto.error.ErrorResponse;
import com.fh.scm.exceptions.UserException;
import com.fh.scm.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/user", produces = "application/json; charset=UTF-8")
public class APIUserController {

    private final JWTService jwtService;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<?> list(@RequestParam(required = false, defaultValue = "") Map<String, String> params) {
        List<UserResponse> userResponseList = this.userService.getAll(params);

        return ResponseEntity.ok(userResponseList);
    }

    @RequestMapping(path = "/{userId}", method = {RequestMethod.GET, RequestMethod.DELETE})
    public ResponseEntity<?> details(HttpServletRequest request, @PathVariable(value = "userId") Long id) {
        if (!this.userService.exists(id)) {
            return ResponseEntity.notFound().build();
        }

        if (request.getMethod().equals("GET")) {
            UserResponse userResponse = this.userService.getUserResponse(id);

            return ResponseEntity.ok(userResponse);
        }

        if (request.getMethod().equals("DELETE")) {
            this.userService.delete(id);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();
    }

    @PostMapping(path = "/token")
    public ResponseEntity<?> authenticateUser(@RequestBody @Valid UserRequestLogin userRequestLogin) {
        if (this.userService.authenticateUser(userRequestLogin.getUsername(), userRequestLogin.getPassword())) {
            String token = this.jwtService.generateTokenLogin(userRequestLogin.getUsername());
            this.userService.updateLastLogin(userRequestLogin.getUsername());

            return ResponseEntity.ok(token);
        }

        return ResponseEntity.badRequest().body("Sai tên đăng nhập hoặc mật khẩu");
    }

    @PostMapping(path = "/register")
    public ResponseEntity<?> register(@RequestBody @Valid UserRequestRegister userRequestRegister, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<ErrorResponse> errorMessages = ErrorResponse.fromBindingResult(bindingResult);

            return ResponseEntity.badRequest().body(errorMessages);
        }

        UserResponse userResponse;
        try {
            userResponse = this.userService.register(userRequestRegister);
        } catch (UserException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }

    @PostMapping(path = "/confirm")
    public ResponseEntity<?> confirm(Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Bạn cần đăng nhập để xác nhận tài khoản");
        }

        if (this.userService.confirm(principal.getName())) {
            return ResponseEntity.ok().body("Xác nhận tài khoản thành công");
        }

        return ResponseEntity.badRequest().body("Xác nhận tài khoản không thành công");
    }

    @GetMapping(path = "/profile")
    public ResponseEntity<?> profile(Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Bạn cần đăng nhập để xem thông tin cá nhân");
        }

        UserResponse userResponse = this.userService.profile(principal.getName());

        return ResponseEntity.ok(userResponse);
    }

    @PatchMapping(path = "/profile/update")
    public ResponseEntity<?> update(Principal principal, @RequestBody @Valid UserRequestUpdate userRequestUpdate, BindingResult bindingResult) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Bạn cần đăng nhập để cập nhật thông tin cá nhân");
        }

        if (bindingResult.hasErrors()) {
            List<ErrorResponse> errorMessages = ErrorResponse.fromBindingResult(bindingResult);

            return ResponseEntity.badRequest().body(errorMessages);
        }

        UserResponse userResponse;
        try {
            userResponse = this.userService.updateProfile(principal.getName(), userRequestUpdate);
        } catch (UserException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }

        return ResponseEntity.ok(userResponse);
    }
}
