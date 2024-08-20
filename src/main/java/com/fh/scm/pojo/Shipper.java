package com.fh.scm.pojo;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "shipper")
public class Shipper extends _BaseEntity implements Serializable {

    @NotNull(message = "{shipper.name.notnull}")
    @Column(nullable = false)
    private String name;

    @Builder.Default
    @DecimalMin(value = "1.00", message = "{rating.min")
    @DecimalMax(value = "5.00", message = "{rating.max")
    @Column(precision = 2, scale = 1, columnDefinition = "float default 0.0")
    private Float rating = 0.0f;

    @NotNull(message = "{shipper.contactInfo.notnull}")
    @Column(name = "contact_info", nullable = false)
    private String contactInfo;

    @OneToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "shipper")
    private Set<Shipment> shipmentSet;

    @Override
    public String toString() {
        return "com.fh.scm.pojo.Shipper[ id=" + this.id + " ]";
    }
}
