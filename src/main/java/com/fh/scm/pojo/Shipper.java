package com.fh.scm.pojo;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import java.io.Serializable;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "shipper")
public class Shipper extends BaseEntity implements Serializable {

    @Column(nullable = false)
    private String name;

    @Builder.Default
    @DecimalMin(value = "1.00", inclusive = true, message = "...")
    @DecimalMax(value = "5.00", inclusive = true, message = "...")
    @Column(nullable = false, precision = 2, scale = 1, columnDefinition = "float default 0.0")
    private Float rating = 0.0f;

    @Column(name = "contact_info", nullable = false)
    private String contactInfo;

    @MapsId
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false, orphanRemoval = true)
    @JoinColumn(name = "id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private User user;

    @OneToMany(mappedBy = "shipper")
    private Set<Shipment> shipmentSet;
}
