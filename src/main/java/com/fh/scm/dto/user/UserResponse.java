package com.fh.scm.dto.user;

import com.fh.scm.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private UUID id;

    @NotNull
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", message = "...")
    private String email;

    @NotNull
    @Size(min = 1, max = 50, message = "...")
    private String username;

    private String avatar;

    @NotNull
    private Role role;

    private Boolean isConfirm;

    private LocalDateTime lastLogin;
}
