package com.fh.scm.dto.api.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fh.scm.enums.OrderStatus;
import com.fh.scm.enums.OrderType;
import com.fh.scm.pojo.OrderDetails;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {

    private Long id;

    private String orderNumber;

    private OrderType type;

    private OrderStatus status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime orderDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date expectedDelivery;

    private Set<OrderDetailsReponse> orderDetailsSet;
}
