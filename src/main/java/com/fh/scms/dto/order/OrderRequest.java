package com.fh.scms.dto.order;

import com.fh.scms.enums.OrderType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {

    @NotNull(message = "{order.type.notNull}")
    private OrderType type;

    @Valid
    private Set<OrderDetailsRequest> orderDetails;

    private Long inventoryId; // Cho đơn hàng nhập

    private LocalDateTime createdAt;
}
