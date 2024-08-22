package com.fh.scm.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fh.scm.enums.DeliveryMethodType;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import javax.validation.constraints.NotBlank;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "delivery_schedule")
public class DeliverySchedule extends _BaseEntity implements Serializable {

    @NotNull(message = "{deliverySchedule.scheduledDate.notNull}")
    @NotBlank(message = "Ngày giao hàng không được để trống")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(nullable = false)
    private LocalDateTime scheduledDate;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @NotNull(message = "{deliverySchedule.method.notNull}")
    @NotBlank(message = "Phương thức giao hàng không được để trống")
    @Column(nullable = false)
    private DeliveryMethodType method = DeliveryMethodType.EXPRESS;

    @OneToMany(mappedBy = "deliverySchedule")
    private Set<Order> orderSet;

    @OneToMany(mappedBy = "deliverySchedule", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Shipment> shipmentSet;

    @Override
    public String toString() {
        return "com.fh.scm.pojo.DeliverySchedule[ id=" + this.id + " ]";
    }
}
