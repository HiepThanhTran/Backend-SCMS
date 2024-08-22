package com.fh.scm.pojo;

import lombok.*;

import javax.persistence.*;
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
public class Inventory extends _BaseEntity implements Serializable {

    @NotNull(message = "{inventory.name.notNull}")
    @Column(nullable = false, unique = true)
    private String name;

    @Builder.Default
    @Column(nullable = false, columnDefinition = "float default 0")
    private Float quantity = 0.0f;

    @ManyToOne(cascade = {CascadeType.PERSIST}, optional = false)
    @JoinColumn(name = "warehouse_id", referencedColumnName = "id", nullable = false)
    private Warehouse warehouse;

    @OneToMany(mappedBy = "inventory", cascade = CascadeType.ALL)
    private Set<Product> productSet;

    @Override
    public String toString() {
        return "com.fh.scm.pojo.Inventory[ id=" + this.id + " ]";
    }
}
