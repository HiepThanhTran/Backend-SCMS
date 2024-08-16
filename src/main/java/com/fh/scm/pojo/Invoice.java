package com.fh.scm.pojo;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "invoice")
public class Invoice extends BaseEntity implements Serializable {

    @Builder.Default
    @Column(name = "is_paid", nullable = false, columnDefinition = "boolean default false")
    private Boolean isPaid = false;

    @Builder.Default
    @Column(name = "total_amount", nullable = false, precision = 11, scale = 2, columnDefinition = "decimal default 0.0")
    private BigDecimal totalAmount = BigDecimal.ZERO;

    @ManyToOne(cascade = {CascadeType.PERSIST}, optional = false)
    @JoinColumn(name = "tax_id", referencedColumnName = "id")
    private Tax tax;

    @ManyToOne(cascade = {CascadeType.PERSIST}, optional = false)
    @JoinColumn(name = "payment_terms_id", referencedColumnName = "id")
    private PaymentTerms paymentTerms;

    @OneToMany(mappedBy = "invoice", cascade = {CascadeType.PERSIST})
    private Set<Order> orderSet;
}
