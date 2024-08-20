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
public class Supplier extends _BaseEntity implements Serializable {

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

    @OneToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @JsonIgnore
    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL)
    private Set<Rating> ratingSet;

    @JsonIgnore
    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL)
    private Set<SupplierCosting> supplierCostingSet;

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "supplier_payment_terms",
            joinColumns = @JoinColumn(name = "supplier_id"),
            inverseJoinColumns = @JoinColumn(name = "payment_terms_id"))
    private Set<PaymentTerms> paymentTermsSet;

    @Override
    public String toString() {
        return "com.fh.scm.pojo.Supplier[ id=" + this.id + " ]";
    }
}
