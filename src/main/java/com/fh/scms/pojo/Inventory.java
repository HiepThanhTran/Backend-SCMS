package com.fh.scms.pojo;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "inventory")
public class Inventory extends BaseEntity implements Serializable {

    @NotNull(message = "{inventory.name.notNull}")
    @NotBlank(message = "{inventory.name.notNull}")
    @Column(nullable = false, unique = true)
    private String name;

    @ManyToOne(optional = false)
    @NotNull(message = "{inventory.warehouse.notNull}")
    @JoinColumn(name = "warehouse_id", referencedColumnName = "id", nullable = false)
    private Warehouse warehouse;

    @OneToMany(mappedBy = "inventory", cascade = CascadeType.ALL)
    private Set<InventoryDetails> inventoryDetailsSet;

    @Override
    public String toString() {
        return "com.fh.scm.pojo.Inventory[ id=" + this.id + " ]";
    }
}
