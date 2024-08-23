package com.fh.scms.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public enum OrderStatus {

    PENDING("Đang chờ xử lý"),
    CONFIRMED("Đã xác nhận"),
    SHIPPED("Đã chuyển hàng"),
    DELIVERED("Đã giao hàng"),
    CANCELLED("Đã hủy"),
    RETURNED("Đã trả hàng");

    private final String displayName;

    public static Map<String, String> getAllDisplayNames() {
        return Arrays.stream(OrderStatus.values())
                .collect(Collectors.toMap(OrderStatus::name, OrderStatus::getDisplayName));
    }
}
