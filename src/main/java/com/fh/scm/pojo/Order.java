package com.fh.scm.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fh.scm.enums.OrderStatus;
import com.fh.scm.enums.OrderType;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "\"order\"")
public class Order extends BaseEntity implements Serializable {

    @Column(name = "expected_delivery")
    private Date expectedDelivery;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @NotNull(message = "{order.type.notNull}")
    @Column(name = "order_type", nullable = false)
    private OrderType type = OrderType.OUTBOUND;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "order_status", nullable = false)
    private OrderStatus status = OrderStatus.PENDING;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @JsonIgnore
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "invoice_id", referencedColumnName = "id")
    private Invoice invoice;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_schedule_id", referencedColumnName = "id", nullable = false)
    private DeliverySchedule deliverySchedule;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private Set<OrderDetails> orderDetailsSet;
}
