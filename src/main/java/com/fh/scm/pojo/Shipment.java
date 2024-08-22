package com.fh.scm.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fh.scm.enums.ShipmentStatus;
import lombok.*;
import org.eclipse.persistence.annotations.UuidGenerator;

import javax.persistence.*;
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

    private String note;

    @Builder.Default
    @Column(nullable = false, precision = 11, scale = 2, columnDefinition = "decimal default 0.0")
    private BigDecimal cost = BigDecimal.ZERO;

    @Column(name = "current_location", nullable = false)
    private String currentLocation;

    @Builder.Default
    @Column(name = "tracking_number", nullable = false, unique = true, length = 36)
    private String trackingNumber = String.valueOf(UUID.randomUUID());

    @Builder.Default
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
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
