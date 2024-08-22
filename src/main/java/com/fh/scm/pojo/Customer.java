package com.fh.scm.pojo;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customer")
public class Customer extends _BaseEntity implements Serializable {

    @NotNull(message = "{customer.firstName.notNull}")
    @Column(nullable = false)
    private String firstName;

    @NotNull(message = "{customer.middleName.notNull}")
    @Column(nullable = false)
    private String middleName;

    @NotNull(message = "{customer.lastName.notNull}")
    @Column(nullable = false)
    private String lastName;

    @NotNull(message = "{customer.address.notNull}")
    @Column(nullable = false)
    private String address;

    @NotNull(message = "{user.phone.notNull}")
    @Pattern(regexp = "^[0-9]{10,15}$", message = "{user.phone.pattern}")
    @Column(nullable = false, unique = true, length = 15)
    private String phone;

    @Builder.Default
    @Column(columnDefinition = "TINYINT(1) default 1")
    private Boolean gender = true; // true (1): Nữ - false (0): Nam

    private Date dateOfBirth;

    @OneToOne(cascade = CascadeType.ALL, optional = false, orphanRemoval = true)
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
