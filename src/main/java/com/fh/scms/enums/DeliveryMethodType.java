package com.fh.scms.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public enum DeliveryMethodType {

    GROUND("Đường bộ"),
    AIR("Đường hàng không"),
    SEA("Đường biển"),
    EXPRESS("Giao hàng nhanh");

    private final String displayName;

    public static Map<String, String> getAllDisplayNames() {
        return Arrays.stream(DeliveryMethodType.values())
                .collect(Collectors.toMap(DeliveryMethodType::name, DeliveryMethodType::getDisplayName));
    }
}
