package com.fh.scms.pojo;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
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
public class InventoryDetails extends _BaseEntity implements Serializable {

    @Builder.Default
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
}
