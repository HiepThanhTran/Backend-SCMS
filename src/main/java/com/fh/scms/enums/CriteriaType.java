package com.fh.scms.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public enum CriteriaType {

    COST("Giá cả"),
    QUALITY("Chất lượng"),
    TIMELY_DELIVERY("Giao hàng đúng hạn");

    private final String displayName;

    public static Map<String, String> getAllDisplayNames() {
        return Arrays.stream(CriteriaType.values())
                .collect(Collectors.toMap(CriteriaType::name, CriteriaType::getDisplayName));
    }
}
