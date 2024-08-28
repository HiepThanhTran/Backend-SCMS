package com.fh.scms.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fh.scms.enums.UserRole;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "\"user\"", indexes = {
        @Index(name = "idx_user_email", columnList = "email"),
        @Index(name = "idx_user_username", columnList = "username"),
})
public class User extends _BaseEntity implements Serializable {

    @NotNull(message = "{user.email.notNull}")
    @NotBlank(message = "{user.email.notNull}")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", message = "{user.email.pattern}")
    @Column(nullable = false, unique = true)
    private String email;

    @NotNull(message = "{user.username.notNull}")
    @NotBlank(message = "{user.username.notNull}")
    @Size(min = 6, max = 50, message = "{user.username.size}")
    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @NotNull(message = "{user.password.notNull}")
    @NotBlank(message = "{user.password.notNull}")
    @Size(min = 8, max = 300, message = "{user.password.size}")
    @Column(nullable = false, length = 300)
    private String password;

    @Column(length = 300)
    private String avatar;

    @Transient
    private MultipartFile file;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @NotNull(message = "{user.role.notNull}")
    @Column(nullable = false)
    private UserRole userRole = UserRole.ROLE_CUSTOMER;

    @Builder.Default
    @NotNull
    @Column(name = "is_confirm", nullable = false, columnDefinition = "boolean default false")
    private Boolean confirm = false;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    @Column(name = "last_login")
    private LocalDateTime lastLogin;

    @JsonIgnore
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Cart cart;

    @JsonIgnore
    @OneToOne(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.LAZY)
    private Customer customer;

    @JsonIgnore
    @OneToOne(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.LAZY)
    private Supplier supplier;

    @JsonIgnore
    @OneToOne(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.LAZY)
    private Shipper shipper;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Invoice> invoiceSet;

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
