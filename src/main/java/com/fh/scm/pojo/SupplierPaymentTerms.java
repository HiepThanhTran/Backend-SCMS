package com.fh.scm.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "supplier_payment_terms")
public class SupplierPaymentTerms extends BaseEntity implements Serializable {

    @JsonIgnore
    @ManyToOne(optional = false)
    @JoinColumn(name = "supplier_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private Supplier supplier;

    @ManyToOne(optional = false)
    @JoinColumn(name = "payment_terms_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private PaymentTerms paymentTerms;
}
