package com.fh.scm.dto.api.user;

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
public class UserRequestLogin {

    @NotNull(message = "{user.username.notNull}")
    @Size(min = 6, max = 50, message = "{user.username.size}")
    private String username;

    @NotNull(message = "{user.password.notNull}")
    @Size(min = 8, max = 300, message = "{user.password.size}")
    private String password;
}
