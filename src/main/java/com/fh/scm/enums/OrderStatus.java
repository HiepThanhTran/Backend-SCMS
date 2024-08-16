package com.fh.scm.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

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
}
