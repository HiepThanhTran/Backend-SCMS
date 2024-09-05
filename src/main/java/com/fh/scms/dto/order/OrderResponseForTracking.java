package com.fh.scms.dto.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fh.scms.enums.OrderStatus;
import com.fh.scms.enums.OrderType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseForTracking {

    private OrderType orderType;

    private OrderStatus orderStatus;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private Date orderDate;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate scheduledDate;

    private BigDecimal shippingCost;

    private String currentLocation;

    private String trackingNumber;

    private String shipperName;
}
