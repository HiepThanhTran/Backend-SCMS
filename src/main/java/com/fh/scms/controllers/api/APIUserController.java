package com.fh.scms.controllers.api;

import com.fh.scms.components.JWTService;
import com.fh.scms.dto.MessageResponse;
import com.fh.scms.dto.user.UserRequestLogin;
import com.fh.scms.dto.user.UserRequestRegister;
import com.fh.scms.dto.user.UserRequestUpdate;
import com.fh.scms.dto.user.UserResponse;
import com.fh.scms.pojo.User;
import com.fh.scms.services.UserService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/users", produces = "application/json; charset=UTF-8")
public class APIUserController {

    private final JWTService jwtService;
    private final UserService userService;

    @PostMapping(path = "/login")
    public ResponseEntity<?> authenticateUser(@RequestBody @Valid UserRequestLogin userRequestLogin, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(MessageResponse.fromBindingResult(bindingResult));
        }

        if (!this.userService.authenticateUser(userRequestLogin.getUsername(), userRequestLogin.getPassword())) {
            return ResponseEntity.badRequest().body(List.of(new MessageResponse("Tài khoản hoặc mật khẩu không đúng")));
        }

        String token = this.jwtService.generateTokenLogin(userRequestLogin.getUsername());
        this.userService.updateLastLogin(userRequestLogin.getUsername());

        return ResponseEntity.ok(token);
    }

    @PostMapping(path = "/register")
    public ResponseEntity<?> register(@RequestBody @Valid UserRequestRegister userRequestRegister, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(MessageResponse.fromBindingResult(bindingResult));
        }

        UserResponse userResponse = this.userService.registerUser(userRequestRegister);

        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }

    @PostMapping(path = "/confirm")
    public ResponseEntity<?> confirmUser(Principal principal) {
        if (!this.userService.confirmUser(principal.getName())) {
            return ResponseEntity.badRequest().body(List.of(new MessageResponse("Xác nhận tài khoản không thành công")));
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping(path = "/profile")
    public ResponseEntity<?> getProfileUser(Principal principal) {
        UserResponse userResponse = this.userService.getProfileUser(principal.getName());

        return ResponseEntity.ok(userResponse);
    }

    @PostMapping(path = "/profile/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateProfileUser(Principal principal, @ModelAttribute @Valid UserRequestUpdate userRequestUpdate, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(MessageResponse.fromBindingResult(bindingResult));
        }

        UserResponse userResponse = this.userService.updateProfileUser(principal.getName(), userRequestUpdate);

        return ResponseEntity.ok(userResponse);
    }

    @DeleteMapping(path = "/profile/delete")
    public ResponseEntity<?> deleteUser(Principal principal) {
        User user = this.userService.findByUsername(principal.getName());
        this.userService.delete(user.getId());

        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleEntityNotFoundException(@NotNull HttpServletRequest req, EntityNotFoundException e) {
        LoggerFactory.getLogger(req.getRequestURI()).error(e.getMessage(), e);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(List.of(new MessageResponse(e.getMessage())));
    }

    @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class})
    public ResponseEntity<?> handleIllegalArgumentException(@NotNull HttpServletRequest req, IllegalArgumentException e) {
        LoggerFactory.getLogger(req.getRequestURI()).error(e.getMessage(), e);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(List.of(new MessageResponse(e.getMessage())));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDenied(@NotNull HttpServletRequest req, AccessDeniedException e) {
        LoggerFactory.getLogger(req.getRequestURI()).error(e.getMessage(), e);

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(List.of(new MessageResponse(e.getMessage())));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(@NotNull HttpServletRequest req, Exception e) {
        LoggerFactory.getLogger(req.getRequestURI()).error(e.getMessage(), e);

        return ResponseEntity.badRequest().body(List.of(new MessageResponse(e.getMessage())));
    }
}
