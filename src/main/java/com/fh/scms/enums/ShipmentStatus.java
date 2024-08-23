package com.fh.scms.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public enum ShipmentStatus {

    IN_TRANSIT("Đang vận chuyển"),
    DELIVERED("Đã giao hàng"),
    RETURNED("Hoàn trả hàng");

    private final String displayName;

    public static Map<String, String> getAllDisplayNames() {
        return Arrays.stream(ShipmentStatus.values())
                .collect(Collectors.toMap(ShipmentStatus::name, ShipmentStatus::getDisplayName));
    }
}

