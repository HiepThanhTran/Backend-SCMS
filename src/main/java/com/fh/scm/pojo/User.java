package com.fh.scm.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fh.scm.enums.UserRole;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "\"user\"")
public class User extends _BaseEntity implements Serializable {

    @NotNull(message = "{user.email.notNull}")
    @Column(nullable = false, unique = true)
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", message = "{user.email.pattern}")
    private String email;

    @NotNull(message = "{user.username.notNull}")
    @Column(nullable = false, unique = true, length = 50)
    @Size(min = 6, max = 50, message = "{user.username.size}")
    private String username;

    @JsonIgnore
    @Column(nullable = false, length = 300)
    @NotNull(message = "{user.password.notNull}")
    @Size(min = 8, max = 300, message = "{user.password.size}")
    private String password;

    @Column(length = 300)
    private String avatar;

    @Builder.Default
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull(message = "{user.role.notNull}")
    private UserRole userRole = UserRole.ROLE_CUSTOMER;

    @Builder.Default
    @NotNull
    @Column(name = "is_confirm", nullable = false, columnDefinition = "boolean default false")
    private Boolean isConfirm = false;

    @Column(name = "last_login")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastLogin;

    @JsonIgnore
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Customer customer;

    @JsonIgnore
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Supplier supplier;

    @JsonIgnore
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Shipper shipper;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Order> orderSet;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Rating> ratingSet;

    @Override
    public String toString() {
        return "com.fh.scm.pojo.User[ id=" + this.id + " ]";
    }
}
