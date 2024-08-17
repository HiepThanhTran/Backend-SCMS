package com.fh.scm.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginRequest {

    @NotNull
    @Size(min = 1, max = 50, message = "...")
    private String username;

    @NotNull
    @Size(min = 1, max = 300, message = "...")
    private String password;
}
