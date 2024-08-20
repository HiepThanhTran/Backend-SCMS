package com.fh.scm.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "product")
public class Product extends _BaseEntity implements Serializable {

    @NotNull(message = "{product.name.notNull}")
    @Column(nullable = false, unique = true)
    private String name;

    @Builder.Default
    @NotNull(message = "{product.price.notNull}")
    @Column(nullable = false, precision = 11, scale = 2, columnDefinition = "decimal default 0.0")
    private BigDecimal price = BigDecimal.ZERO;

    @Column(length = 300)
    private String image;

    private String description;

    @NotNull(message = "{product.expiryDate.notNull}")
    @Column(name = "expiry_date", nullable = false)
    private Date expiryDate;

    @ManyToOne(cascade = {CascadeType.PERSIST}, optional = false)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;

    @ManyToMany
    @JoinTable(name = "product_unit",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "unit_id"))
    private Set<Unit> unitSet;

    @ManyToMany
    @JoinTable(name = "product_tag",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tagSet;

    @JsonIgnore
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Set<OrderDetails> orderDetailsSet;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Set<SupplierCosting> supplierCostingSet;

    @JsonIgnore
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Set<InventoryDetails> inventoryDetailsSet;

    @Override
    public String toString() {
        return "com.fh.scm.pojo.Product[ id=" + this.id + " ]";
    }
}
