package com.fh.scms.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public enum OrderType {

    INBOUND("Nhập kho"),
    OUTBOUND("Xuất kho");

    private final String displayName;

    public static Map<String, String> getAllDisplayNames() {
        return Arrays.stream(OrderType.values())
                .collect(Collectors.toMap(OrderType::name, OrderType::getDisplayName));
    }
}
