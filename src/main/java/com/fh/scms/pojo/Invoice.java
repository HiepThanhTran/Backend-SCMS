package com.fh.scms.pojo;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "invoice", indexes = {
        @Index(name = "invoice_number_index", columnList = "invoice_number", unique = true),
})
public class Invoice extends BaseEntity implements Serializable {

    @Builder.Default
    @NotBlank(message = "{invoice.invoiceNumber.notNull}")
    @NotNull(message = "{invoice.invoiceNumber.notNull}")
    @Column(name = "invoice_number", nullable = false, unique = true, length = 36, updatable = false)
    private String invoiceNumber = String.valueOf(UUID.randomUUID());

    @Builder.Default
    @NotNull(message = "{invoice.isPaid.notNull}")
    @Column(name = "is_paid", nullable = false, columnDefinition = "boolean default false")
    private Boolean paid = false;

    @Builder.Default
    @NotNull(message = "{invoice.totalAmount.notNull}")
    @Column(name = "total_amount", nullable = false, precision = 11, scale = 2, columnDefinition = "decimal default 0.0")
    private BigDecimal totalAmount = BigDecimal.ZERO;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @OneToOne(optional = false, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
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
