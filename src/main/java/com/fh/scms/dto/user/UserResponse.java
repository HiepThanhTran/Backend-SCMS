package com.fh.scms.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fh.scms.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private Long id;

    private String email;

    private String username;

    private String avatar;

    private UserRole role;

    private Boolean isConfirm;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private Date lastLogin;
}
