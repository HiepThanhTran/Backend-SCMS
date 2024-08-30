package com.fh.scms.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
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

    private String role;

    private Boolean isConfirm;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private Date lastLogin;
}
