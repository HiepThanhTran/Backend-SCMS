package com.fh.scms.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "invoice")
public class Invoice extends _BaseEntity implements Serializable {

    @Builder.Default
    @Column(name = "invoice_number", nullable = false, unique = true, length = 36)
    private String invoiceNumber = String.valueOf(UUID.randomUUID());

    @Builder.Default
    @Column(name = "is_paid", nullable = false, columnDefinition = "boolean default false")
    private Boolean paid = false;

    @Builder.Default
    @Column(name = "total_amount", nullable = false, precision = 11, scale = 2, columnDefinition = "decimal default 0.0")
    private BigDecimal totalAmount = BigDecimal.ZERO;

    @JsonIgnore
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @OneToOne(optional = false)
    @JoinColumn(name = "order_id", referencedColumnName = "id", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "tax_id", referencedColumnName = "id")
    private Tax tax;

    @Override
    public String toString() {
        return "com.fh.scm.pojo.Invoice[ id=" + this.id + " ]";
    }
}
