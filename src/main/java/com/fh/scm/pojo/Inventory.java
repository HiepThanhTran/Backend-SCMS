package com.fh.scm.pojo;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;
import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "inventory")
public class Inventory extends _BaseEntity implements Serializable {

    @NotNull(message = "{inventory.name.notNull}")
    @NotBlank(message = "Tên hàng tồn kho không được rỗng")
    @Column(nullable = false, unique = true)
    private String name;

    @ManyToOne(cascade = {CascadeType.PERSIST}, optional = false)
    @JoinColumn(name = "warehouse_id", referencedColumnName = "id", nullable = false)
    private Warehouse warehouse;

    @OneToMany(mappedBy = "inventory", cascade = CascadeType.ALL)
    private Set<InventoryDetails> inventoryDetailsSet;

    @Override
    public String toString() {
        return "com.fh.scm.pojo.Inventory[ id=" + this.id + " ]";
    }
}
