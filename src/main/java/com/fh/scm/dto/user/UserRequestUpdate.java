package com.fh.scm.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestUpdate {

    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", message = "{user.email.pattern}")
    private String email;

    @Size(min = 6, max = 50, message = "{user.username.size}")
    private String username;

    @Size(min = 8, max = 300, message = "{user.password.size}")
    private String password;

    private MultipartFile avatar;
}
