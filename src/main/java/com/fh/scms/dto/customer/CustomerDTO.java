package com.fh.scms.dto.customer;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {

    @NotNull(message = "{customer.firstName.notNull}")
    @NotBlank(message = "{customer.firstName.notNull}")
    private String firstName;

    @NotNull(message = "{customer.middleName.notNull}")
    @NotBlank(message = "{customer.middleName.notNull}")
    private String middleName;

    @NotNull(message = "{customer.lastName.notNull}")
    @NotBlank(message = "{customer.lastName.notNull}")
    private String lastName;

    @NotNull(message = "{customer.address.notNull}")
    @NotBlank(message = "{customer.address.notNull}")
    private String address;

    @NotNull(message = "{user.phone.notNull}")
    @NotBlank(message = "{user.phone.notNull}")
    @Pattern(regexp = "^[0-9]{10,15}$", message = "{user.phone.pattern}")
    private String phone;

    private Boolean gender;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date dateOfBirth;
}
