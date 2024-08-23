package com.fh.scms.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fh.scms.enums.OrderStatus;
import com.fh.scms.enums.OrderType;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "\"order\"")
public class Order extends _BaseEntity implements Serializable {

    @Builder.Default
    @Column(name = "order_number", nullable = false, unique = true, length = 36)
    private String orderNumber = String.valueOf(UUID.randomUUID());

    @Builder.Default
    @Column(name = "is_cancel", columnDefinition = "boolean default false")
    private Boolean cancel = false;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @NotNull(message = "{order.type.notNull}")
    @Column(name = "order_type", nullable = false)
    private OrderType type = OrderType.OUTBOUND;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "order_status", nullable = false)
    private OrderStatus status = OrderStatus.PENDING;

    @Column(name = "expected_delivery")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate expectedDelivery;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @JsonIgnore
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "invoice_id", referencedColumnName = "id")
    private Invoice invoice;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_schedule_id", referencedColumnName = "id")
    private DeliverySchedule deliverySchedule;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private Set<OrderDetails> orderDetailsSet;

    @Override
    public String toString() {
        return "com.fh.scm.pojo.Order[ id=" + this.id + " ]";
    }
}
