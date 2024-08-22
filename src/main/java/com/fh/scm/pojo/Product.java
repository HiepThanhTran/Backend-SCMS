package com.fh.scm.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product")
public class Product extends _BaseEntity implements Serializable {

    @NotNull(message = "{product.name.notNull}")
    @NotBlank(message = "{product.name.notNull}")
    @Column(nullable = false, unique = true)
    private String name;

    private String description;

    @Builder.Default
    @NotNull(message = "{product.price.notNull}")
    @Column(nullable = false, precision = 11, scale = 2, columnDefinition = "decimal default 0.0")
    private BigDecimal price = BigDecimal.ZERO;

    @Column(length = 300)
    private String image;

    @NotNull(message = "{product.expiryDate.notNull}")
    @Column(name = "expiry_date", nullable = false)
    private Date expiryDate;

    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;

    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "inventory_id", referencedColumnName = "id")
    private Inventory inventory;

    @ManyToMany(cascade = {CascadeType.PERSIST})
    @JoinTable(name = "product_unit",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "unit_id"))
    private Set<Unit> unitSet;

    @ManyToMany(cascade = {CascadeType.PERSIST})
    @JoinTable(name = "product_tag",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tagSet;

    @JsonIgnore
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Set<OrderDetails> orderDetailsSet;

    @JsonIgnore
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Set<CartDetails> cartDetailsSet;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Set<SupplierCosting> supplierCostingSet;

    @Override
    public String toString() {
        return "com.fh.scm.pojo.Product[ id=" + this.id + " ]";
    }
}
