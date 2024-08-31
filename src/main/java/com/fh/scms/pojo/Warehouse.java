package com.fh.scms.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "warehouse")
public class Warehouse extends BaseEntity implements Serializable {

    @NotNull(message = "{warehouse.name.notNull}")
    @NotBlank(message = "{warehouse.name.notNull}")
    @Column(nullable = false, unique = true)
    private String name;

    @NotNull(message = "{warehouse.location.notNull}")
    @NotBlank(message = "{warehouse.location.notNull}")
    @Column(nullable = false)
    private String location;

    @Builder.Default
    @NotNull(message = "{warehouse.capacity.notNull}")
    @Column(nullable = false, columnDefinition = "float default 0.0")
    private Float capacity = 0.0f;

    @Builder.Default
    @NotNull(message = "{warehouse.cost.notNull}")
    @Column(nullable = false, precision = 11, scale = 2, columnDefinition = "decimal default 0.0")
    private BigDecimal cost = BigDecimal.ZERO;

    @OneToMany(mappedBy = "warehouse", cascade = CascadeType.ALL)
    private Set<Inventory> inventorySet;

    @JsonIgnore
    @OneToMany(mappedBy = "warehouse", cascade = CascadeType.ALL)
    private Set<Shipment> shipmentSet;

    @Override
    public String toString() {
        return "com.fh.scm.pojo.Warehouse[ id=" + this.id + " ]";
    }
}
