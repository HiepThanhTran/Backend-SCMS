package com.fh.scms.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public enum ProductStatus {

    EXPIRING_SOON("Sắp hết hạn"),
    EXPIRED("Đã hết hạn");

    private final String displayName;

    public static Map<String, String> getAllDisplayNames() {
        return Arrays.stream(ProductStatus.values())
                .collect(Collectors.toMap(ProductStatus::name, ProductStatus::getDisplayName));
    }
}
