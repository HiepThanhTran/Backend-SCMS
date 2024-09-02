package com.fh.scms.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
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
@Table(name = "shipper")
public class Shipper extends BaseEntity implements Serializable {

    @NotNull(message = "{shipper.name.notnull}")
    @NotBlank(message = "{shipper.name.notnull}")
    @Column(nullable = false)
    private String name;

    @Builder.Default
    @DecimalMin(value = "1.00", message = "{rating.min")
    @DecimalMax(value = "5.00", message = "{rating.max")
    @Column(precision = 2, scale = 1, columnDefinition = "float default 5.0")
    private BigDecimal rating = BigDecimal.valueOf(5);

    @NotNull(message = "{shipper.contactInfo.notnull}")
    @NotBlank(message = "{shipper.contactInfo.notnull}")
    @Column(name = "contact_info", nullable = false)
    private String contactInfo;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE}, optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @JsonIgnore
    @OneToMany(mappedBy = "shipper", cascade = CascadeType.ALL)
    private Set<Shipment> shipmentSet;

    @Override
    public String toString() {
        return "com.fh.scm.pojo.Shipper[ id=" + this.id + " ]";
    }
}
