package com.fh.scm.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fh.scm.enums.ShipmentStatus;
import lombok.*;
import org.eclipse.persistence.annotations.UuidGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "shipment")
public class Shipment extends BaseEntity implements Serializable {

    private String note;

    @Builder.Default
    @Column(nullable = false, precision = 11, scale = 2, columnDefinition = "decimal default 0.0")
    private BigDecimal cost = BigDecimal.ZERO;

    @Column(name = "current_location", nullable = false)
    private String currentLocation;

    @UuidGenerator(name = "uuid")
    @GeneratedValue(generator = "uuid")
    @Column(name = "tracking_number", nullable = false, unique = true)
    private String trackingNumber;

    @Builder.Default
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ShipmentStatus status = ShipmentStatus.IN_TRANSIT;

    @JsonIgnore
    @ManyToOne(optional = false)
    @JoinColumn(name = "shipper_id", referencedColumnName = "id")
    private Shipper shipper;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "warehouse_id", referencedColumnName = "id", nullable = false)
    private Warehouse warehouse;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "delivery_schedule_id", referencedColumnName = "id")
    private DeliverySchedule deliverySchedule;
}
