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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
            List<MessageResponse> errorMessages = MessageResponse.fromBindingResult(bindingResult);

            return ResponseEntity.badRequest().body(errorMessages);
        }

        if (!this.userService.authenticateUser(userRequestLogin.getUsername(), userRequestLogin.getPassword())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Tài khoản hoặc mật khẩu không đúng"));
        }

        String token = this.jwtService.generateTokenLogin(userRequestLogin.getUsername());
        this.userService.updateLastLogin(userRequestLogin.getUsername());

        return ResponseEntity.ok(token);
    }

    @PostMapping(path = "/register")
    public ResponseEntity<?> register(@RequestBody @Valid UserRequestRegister userRequestRegister, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<MessageResponse> errorMessages = MessageResponse.fromBindingResult(bindingResult);

            return ResponseEntity.badRequest().body(errorMessages);
        }

        try {
            UserResponse userResponse = this.userService.registerUser(userRequestRegister);

            return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @PostMapping(path = "/confirm")
    public ResponseEntity<?> confirmUser(Principal principal) {
        if (!this.userService.confirmUser(principal.getName())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Xác nhận tài khoản không thành công"));
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping(path = "/profile")
    public ResponseEntity<?> getProfileUser(Principal principal) {
        UserResponse userResponse = this.userService.getProfileUser(principal.getName());

        return ResponseEntity.ok(userResponse);
    }

    @PostMapping(path = "/profile/update")
    public ResponseEntity<?> updateProfileUser(Principal principal, @ModelAttribute @Valid UserRequestUpdate userRequestUpdate, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<MessageResponse> errorMessages = MessageResponse.fromBindingResult(bindingResult);

            return ResponseEntity.badRequest().body(errorMessages);
        }

        try {
            UserResponse userResponse = this.userService.updateProfileUser(principal.getName(), userRequestUpdate);

            return ResponseEntity.ok(userResponse);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @DeleteMapping(path = "/profile/delete")
    public ResponseEntity<?> deleteUser(Principal principal) {
        User user = this.userService.findByUsername(principal.getName());
        if (Optional.ofNullable(user).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        this.userService.delete(user.getId());
        return ResponseEntity.noContent().build();
    }
}
