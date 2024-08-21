package com.fh.scm.controllers.api;

import com.fh.scm.components.JWTService;
import com.fh.scm.dto.ResponseMessage;
import com.fh.scm.dto.api.user.UserRequestLogin;
import com.fh.scm.dto.api.user.UserRequestRegister;
import com.fh.scm.dto.api.user.UserRequestUpdate;
import com.fh.scm.dto.api.user.UserResponse;
import com.fh.scm.exceptions.UserException;
import com.fh.scm.pojo.User;
import com.fh.scm.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/user", produces = "application/json; charset=UTF-8")
public class APIUserController {

    private final JWTService jwtService;
    private final UserService userService;

    @PostMapping(path = "/login")
    public ResponseEntity<?> authenticateUser(@RequestBody @Valid UserRequestLogin userRequestLogin, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<ResponseMessage> errorMessages = ResponseMessage.fromBindingResult(bindingResult);

            return ResponseEntity.badRequest().body(errorMessages);
        }

        if (!this.userService.authenticateUser(userRequestLogin.getUsername(), userRequestLogin.getPassword())) {
            ResponseMessage responseMessage = new ResponseMessage("Tài khoản hoặc mật khẩu không đúng");

            return ResponseEntity.badRequest().body(responseMessage);
        }

        String token = this.jwtService.generateTokenLogin(userRequestLogin.getUsername());
        this.userService.updateLastLogin(userRequestLogin.getUsername());

        return ResponseEntity.ok(token);
    }

    @PostMapping(path = "/register")
    public ResponseEntity<?> register(@RequestBody @Valid UserRequestRegister userRequestRegister, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<ResponseMessage> errorMessages = ResponseMessage.fromBindingResult(bindingResult);

            return ResponseEntity.badRequest().body(errorMessages);
        }

        try {
            UserResponse userResponse = this.userService.register(userRequestRegister);

            return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
        } catch (UserException e) {
            return ResponseEntity.badRequest().body(new ResponseMessage(e.getMessage()));
        }
    }

    @PostMapping(path = "/confirm")
    public ResponseEntity<?> confirm(Principal principal) {
        if (this.userService.confirmUser(principal.getName())) {
            ResponseMessage responseMessage = new ResponseMessage("Xác nhận tài khoản thành công");

            return ResponseEntity.ok().body(responseMessage);
        }

        ResponseMessage responseMessage = new ResponseMessage("Xác nhận tài khoản không thành công");

        return ResponseEntity.badRequest().body(responseMessage);
    }

    @GetMapping(path = "/profile")
    public ResponseEntity<?> profileUser(Principal principal) {
        UserResponse userResponse = this.userService.getProfileUser(principal.getName());

        return ResponseEntity.ok(userResponse);
    }

    @PostMapping(path = "/profile/update")
    public ResponseEntity<?> updateProfileUser(Principal principal, @ModelAttribute @Valid UserRequestUpdate userRequestUpdate, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<ResponseMessage> errorMessages = ResponseMessage.fromBindingResult(bindingResult);

            return ResponseEntity.badRequest().body(errorMessages);
        }

        try {
            UserResponse userResponse = this.userService.updateProfileUser(principal.getName(), userRequestUpdate);

            return ResponseEntity.ok(userResponse);
        } catch (UserException e) {
            return ResponseEntity.badRequest().body(new ResponseMessage(e.getMessage()));
        }
    }

    @DeleteMapping(path = "/profile/delete")
    public ResponseEntity<?> delete(Principal principal) {
        User user = this.userService.getByUsername(principal.getName());

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        this.userService.delete(user.getId());

        return ResponseEntity.noContent().build();
    }
}
