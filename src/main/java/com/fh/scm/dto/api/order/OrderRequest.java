package com.fh.scm.dto.api.order;

import com.fh.scm.enums.OrderStatus;
import com.fh.scm.enums.OrderType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Date;
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
}
