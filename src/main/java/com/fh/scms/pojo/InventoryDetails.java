package com.fh.scms.pojo;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "inventory_details", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"inventory_id", "product_id"})
})
public class InventoryDetails extends BaseEntity implements Serializable {

    @Builder.Default
    @NotNull(message = "{inventoryDetails.quantity.notNull}")
    @Column(nullable = false, columnDefinition = "float default 0")
    private Float quantity = 0.0f;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "inventory_id", referencedColumnName = "id", nullable = false)
    private Inventory inventory;

    @OneToMany(mappedBy = "inventoryDetails", cascade = CascadeType.ALL)
    private Set<OrderDetails> orderDetailsSet;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InventoryDetails)) return false;
        if (!super.equals(o)) return false;
        InventoryDetails that = (InventoryDetails) o;
        return Objects.equals(product, that.product) && Objects.equals(inventory, that.inventory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), product, inventory);
    }
}
