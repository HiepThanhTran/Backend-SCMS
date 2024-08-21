package com.fh.scm.pojo;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;
import javax.validation.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "customer")
public class Customer extends _BaseEntity implements Serializable {

    @Column(nullable = false)
    @NotNull(message = "{customer.firstName.notNull}")
    @NotBlank(message = "First name không được để trống")
    private String firstName;

    @Column(nullable = false)
    @NotNull(message = "{customer.middleName.notNull}")
    @NotBlank(message = "Middle name không được để trống")
    private String middleName;

    @Column(nullable = false)
    @NotNull(message = "{customer.lastName.notNull}")
    @NotBlank(message = "Last name không được để trống")
    private String lastName;

    @Column(nullable = false)
    @NotNull(message = "{customer.address.notNull}")
    @NotBlank(message = "Địa chỉ không được để trống")
    private String address;

    @NotNull(message = "{user.phone.notNull}")
    @NotBlank(message = "Số điện thoại không được để trống")
    @Column(nullable = false, unique = true, length = 15)
    @Pattern(regexp = "^[0-9]{10,15}$", message = "{user.phone.pattern}")
    @NotBlank(message = "Số điện thoại chỉ từ 0 đến 9 và có chiều dài từ 10 đến 15 kí tự")
    private String phone;

    private Boolean gender; // true (1): Nữ - false (0): Nam
    
    
    private Date dateOfBirth;

    @OneToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    public String getFullName() {
        return this.lastName + " " + this.middleName + " " + this.firstName;
    }

    @Override
    public String toString() {
        return "com.fh.scm.pojo.Customer[ id=" + this.id + " ]";
    }
}
