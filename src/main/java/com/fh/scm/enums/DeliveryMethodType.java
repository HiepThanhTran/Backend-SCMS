package com.fh.scm.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DeliveryMethodType {

    GROUND("Đường bộ"),
    AIR("Đường hàng không"),
    SEA("Đường biển"),
    EXPRESS("Giao hàng nhanh");

    private final String displayName;
}
