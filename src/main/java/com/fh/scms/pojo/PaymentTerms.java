package com.fh.scms.pojo;

import com.fh.scms.enums.PaymentTermType;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "payment_terms")
public class PaymentTerms extends _BaseEntity implements Serializable {

    @Builder.Default
    @NotNull(message = "{paymentTerms.discountDays.notNull}")
    @Column(name = "discount_days", nullable = false, columnDefinition = "int default 0.0")
    private Integer discountDays = 0; // Số ngày hưởng chiết khấu

    @Builder.Default
    @DecimalMin(value = "0.01", message = "{paymentTerms.discountPercentage.min}")
    @DecimalMax(value = "1.00", message = "{paymentTerms.discountPercentage.max}")
    @Column(name = "discount_percentage", precision = 5, scale = 2, columnDefinition = "float default 0.01")
    private BigDecimal discountPercentage = BigDecimal.valueOf(0.01); // Phần trăm chiết khấu (nếu có)

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @NotNull(message = "{paymentTerms.termType.notNull}")
    @Column(name = "term_type", nullable = false)
    private PaymentTermType type = PaymentTermType.COD;

    @ManyToOne(optional = false, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @NotNull(message = "{paymentTerms.supplier.notNull}")
    @JoinColumn(name = "supplier_id", referencedColumnName = "id", nullable = false)
    private Supplier supplier;

    @OneToMany(mappedBy = "paymentTerms")
    private Set<Invoice> invoiceSet;

    @Override
    public String toString() {
        return "com.fh.scm.pojo.PaymentTerms[ id=" + this.id + " ]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PaymentTerms)) return false;
        if (!super.equals(o)) return false;
        PaymentTerms that = (PaymentTerms) o;
        return Objects.equals(discountDays, that.discountDays) && Objects.equals(discountPercentage, that.discountPercentage) && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), discountDays, discountPercentage, type);
    }
}
