package com.fh.scms.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "supplier_costing", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"product_id", "supplier_id"})
})
public class SupplierCosting extends _BaseEntity implements Serializable {

    @Builder.Default
    @NotNull(message = "{supplier.costing.unitPrice.notNull}")
    @Column(name = "unit_price", nullable = false, precision = 11, scale = 2, columnDefinition = "decimal default 0.0")
    private BigDecimal unitPrice = BigDecimal.ZERO;

    @Builder.Default
    @NotNull(message = "{supplier.costing.shippingCost.notNull}")
    @Column(name = "shipping_cost", nullable = false, precision = 11, scale = 2, columnDefinition = "decimal default 0.0")
    private BigDecimal shippingCost = BigDecimal.ZERO;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "supplier_id", referencedColumnName = "id", nullable = false)
    private Supplier supplier;
}

