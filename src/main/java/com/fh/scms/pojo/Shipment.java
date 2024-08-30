package com.fh.scms.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fh.scms.enums.ShipmentStatus;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "shipment")
public class Shipment extends _BaseEntity implements Serializable {

    @Builder.Default
    @NotNull(message = "{shipment.cost.notNull}")
    @Column(nullable = false, precision = 11, scale = 2, columnDefinition = "decimal default 0.0")
    private BigDecimal cost = new BigDecimal(0);

    private String note;

    @Column(name = "current_location")
    private String currentLocation;

    @Builder.Default
    @NotNull(message = "{shipment.trackingNumber.notNull}")
    @Column(name = "tracking_number", nullable = false, unique = true, length = 36, updatable = false)
    private String trackingNumber = String.valueOf(UUID.randomUUID());

    @Builder.Default
    @NotNull(message = "{shipment.status.notNull}")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ShipmentStatus status = ShipmentStatus.IN_TRANSIT;

    @JsonIgnore
    @ManyToOne(optional = false)
    @JoinColumn(name = "shipper_id", referencedColumnName = "id", nullable = false)
    private Shipper shipper;

    @ManyToOne(optional = false)
    @JoinColumn(name = "warehouse_id", referencedColumnName = "id", nullable = false)
    private Warehouse warehouse;

    @JsonIgnore
    @ManyToOne(optional = false)
    @JoinColumn(name = "delivery_schedule_id", referencedColumnName = "id", nullable = false)
    private DeliverySchedule deliverySchedule;

    @Override
    public String toString() {
        return "com.fh.scm.pojo.Shipment[ id=" + this.id + " ]";
    }
}
