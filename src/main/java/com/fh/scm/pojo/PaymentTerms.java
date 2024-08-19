package com.fh.scm.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fh.scm.enums.PaymentTermType;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "payment_terms")
public class PaymentTerms extends BaseEntity implements Serializable {

    @Builder.Default
    @NotNull(message = "{paymentTerms.discountDays.notNull}")
    @Column(name = "discount_days", nullable = false, columnDefinition = "int default 0.0")
    private Integer discountDays = 0; // Số ngày hưởng chiết khấu

    @Builder.Default
    @NotNull(message = "{paymentTerms.discountPercentage.notNull}")
    @DecimalMin(value = "0.01", message = "{paymentTerms.discountPercentage.min}")
    @DecimalMax(value = "1.00", message = "{paymentTerms.discountPercentage.max}")
    @Column(name = "discount_percentage", precision = 5, scale = 2, columnDefinition = "float default 0.0")
    private Float discountPercentage = 0.0f; // Phần trăm chiết khấu (nếu có)

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @NotNull(message = "{paymentTerms.termType.notNull}")
    @Column(name = "term_type", nullable = false)
    private PaymentTermType type = PaymentTermType.COD;

    @JsonIgnore
    @OneToMany(mappedBy = "paymentTerms", cascade = {CascadeType.PERSIST})
    private Set<Invoice> invoiceSet;

    @JsonIgnore
    @OneToMany(mappedBy = "paymentTerms", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private Set<SupplierPaymentTerms> supplierPaymentTermsSet;
}
