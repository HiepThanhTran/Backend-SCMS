package com.fh.scm.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "supplier")
public class Supplier extends BaseEntity implements Serializable {

    @NotNull(message = "{supplier.name.notNull}")
    @Column(nullable = false)
    private String name;

    @NotNull(message = "{supplier.address.notNull}")
    @Column(nullable = false)
    private String address;

    @Column(nullable = false, unique = true, length = 15)
    @NotNull(message = "{user.phone.notNull}")
    @Pattern(regexp = "^[0-9]{10,15}$", message = "{user.phone.pattern}")
    private String phone;

    @NotNull(message = "{supplier.contactInfo.notNull}")
    @Column(name = "contact_info", nullable = false)
    private String contactInfo;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToMany(mappedBy = "supplier", cascade = {CascadeType.REMOVE})
    private Set<Rating> ratingSet;

    @JsonIgnore
    @OneToMany(mappedBy = "supplier", cascade = {CascadeType.REMOVE})
    private Set<SupplierCosting> supplierCostingSet;

    @OneToMany(mappedBy = "supplier", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private Set<SupplierPaymentTerms> supplierPaymentTermsSet;
}
