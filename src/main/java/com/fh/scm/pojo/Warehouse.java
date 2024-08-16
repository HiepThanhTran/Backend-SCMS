package com.fh.scm.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "warehouse")
public class Warehouse extends BaseEntity implements Serializable {

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String location;

    @Builder.Default
    @Column(nullable = false, columnDefinition = "float default 0.0")
    private Float capacity = 0.0f;

    @Builder.Default
    @Column(nullable = false, precision = 11, scale = 2, columnDefinition = "decimal default 0.0")
    private BigDecimal cost = BigDecimal.ZERO;

    @OneToMany(mappedBy = "warehouse", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private Set<Inventory> inventorySet;

    @JsonIgnore
    @OneToMany(mappedBy = "warehouse", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private Set<Shipment> shipmentSet;
}
