package com.fh.scm.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ShipmentStatus {

    IN_TRANSIT("Đang vận chuyển"),
    DELIVERED("Đã giao hàng"),
    RETURNED("Hoàn trả hàng");

    private final String displayName;
}

