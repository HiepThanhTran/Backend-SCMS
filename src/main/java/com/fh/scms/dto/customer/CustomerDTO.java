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

    private String firstName;

    private String middleName;

    private String lastName;

    private String address;

    @Pattern(regexp = "^[0-9]{10,15}$", message = "{user.phone.pattern}")
    private String phone;

    private Boolean gender;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date dateOfBirth;
}
