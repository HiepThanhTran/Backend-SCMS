package com.fh.scms.dto.user;

import com.fh.scms.dto.pt.PaymentTermsRequest;
import com.fh.scms.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestRegister {

    @NotNull(message = "{user.email.notNull}")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", message = "{user.email.pattern}")
    private String email;

    @NotNull(message = "{user.username.notNull}")
    @Size(min = 6, max = 50, message = "{user.username.size}")
    private String username;

    @NotNull(message = "{user.password.notNull}")
    @Size(min = 8, max = 300, message = "{user.password.size}")
    private String password;

    private MultipartFile avatar;

    @NotNull(message = "{user.role.notNull}")
    private UserRole userRole;

    @Pattern(regexp = "^[0-9]{10,15}$", message = "{user.phone.pattern}")
    private String phone;

    private String name;

    private String contactInfo;

    private String address;

    private String lastName;

    private String middleName;

    private String firstName;

    private Boolean gender;

    private Date dateOfBirth;

    @Valid
    private Set<PaymentTermsRequest> paymentTermsSet;
}
