package com.fh.scm.pojo;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "customer")
public class Customer extends BaseEntity implements Serializable {

    @Column(nullable = false)
    @NotNull(message = "{customer.firstName.notNull}")
    private String firstName;

    @Column(nullable = false)
    @NotNull(message = "{customer.middleName.notNull}")
    private String middleName;

    @Column(nullable = false)
    @NotNull(message = "{customer.lastName.notNull}")
    private String lastName;

    @Column(nullable = false)
    @NotNull(message = "{customer.address.notNull}")
    private String address;

    @NotNull(message = "{user.phone.notNull}")
    @Column(nullable = false, unique = true, length = 15)
    @Pattern(regexp = "^[0-9]{10,15}$", message = "{user.phone.pattern}")
    private String phone;

    private Boolean gender; // true (1): Nữ - false (0): Nam

    private Date dateOfBirth;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public String getFullName() {
        return this.lastName + " " + this.middleName + " " + this.firstName;
    }
}
