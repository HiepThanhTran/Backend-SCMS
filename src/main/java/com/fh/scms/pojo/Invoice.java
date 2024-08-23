package com.fh.scms.pojo;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "invoice")
public class Invoice extends _BaseEntity implements Serializable {

    @Builder.Default
    @Column(name = "is_paid", nullable = false, columnDefinition = "boolean default false")
    private Boolean isPaid = false;

    @Builder.Default
    @Column(name = "total_amount", nullable = false, precision = 11, scale = 2, columnDefinition = "decimal default 0.0")
    private BigDecimal totalAmount = BigDecimal.ZERO;

    @ManyToOne(optional = false)
    @JoinColumn(name = "tax_id", referencedColumnName = "id")
    private Tax tax;

    @ManyToOne(optional = false, cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "payment_terms_id", referencedColumnName = "id")
    private PaymentTerms paymentTerms;

    @OneToMany(mappedBy = "invoice")
    private Set<Order> orderSet;

    @Override
    public String toString() {
        return "com.fh.scm.pojo.Invoice[ id=" + this.id + " ]";
    }
}
