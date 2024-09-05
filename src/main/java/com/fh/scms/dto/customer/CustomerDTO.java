package com.fh.scms.dto.customer;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Pattern;
import java.time.LocalDate;

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

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate dateOfBirth;
}
