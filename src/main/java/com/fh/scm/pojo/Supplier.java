package com.fh.scm.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Set;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "supplier")
public class Supplier extends _BaseEntity implements Serializable {

    @NotNull(message = "{supplier.name.notNull}")
    @NotBlank(message = "{supplier.name.notNull}")
    @Column(nullable = false)
    private String name;

    @NotNull(message = "{supplier.address.notNull}")
    @NotBlank(message = "{supplier.address.notNull}")
    @Column(nullable = false)
    private String address;

    @NotNull(message = "{user.phone.notNull}")
    @NotBlank(message = "{user.phone.notNull}")
    @Pattern(regexp = "^[0-9]{10,15}$", message = "{user.phone.pattern}")
    @Column(nullable = false, unique = true, length = 15)
    private String phone;

    @NotNull(message = "{supplier.contactInfo.notNull}")
    @NotBlank(message = "{supplier.contactInfo.notNull}")
    @Column(name = "contact_info", nullable = false)
    private String contactInfo;

    @OneToOne(cascade = CascadeType.ALL, optional = false, orphanRemoval = true)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @JsonIgnore
    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL)
    private Set<Rating> ratingSet;

    @JsonIgnore
    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL)
    private Set<SupplierCosting> supplierCostingSet;

    @OneToMany(mappedBy = "supplier", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<PaymentTerms> paymentTermsSet;

    @Override
    public String toString() {
        return "com.fh.scm.pojo.Supplier[ id=" + this.id + " ]";
    }
}
