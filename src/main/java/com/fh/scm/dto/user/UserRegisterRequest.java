package com.fh.scm.dto.user;

import com.fh.scm.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterRequest {

    @NotNull
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", message = "...")
    private String email;

    @NotNull
    @Size(min = 1, max = 50, message = "...")
    private String username;

    @NotNull
    @Size(min = 1, max = 300, message = "...")
    private String password;

    private MultipartFile avatar;

    @NotNull
    private Role role;
}
