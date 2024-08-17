package com.fh.scm.pojo;


import lombok.*;

import javax.persistence.*;
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
    private String lastName;

    @Column(nullable = false)
    private String middleName;

    @Column(nullable = false)
    private String firstName;

    private Boolean gender; // true (1): Nữ - false (0): Nam

    private Date dateOfBirth;

    @Column(nullable = false, unique = true, length = 15)
    @Pattern(regexp = "^[0-9]{15}$", message = "...")
    private String phone;

    @Column(nullable = false)
    private String address;

    @MapsId
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false, orphanRemoval = true)
    @JoinColumn(name = "id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private User user;

    public String getFullName() {
        return this.lastName + " " + this.middleName + " " + this.firstName;
    }
}
