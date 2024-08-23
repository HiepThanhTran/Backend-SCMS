package com.fh.scms.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public enum PaymentTermType {

    EOM("Thanh toán vào cuối tháng"),
    COD("Thanh toán khi nhận hàng"),
    PREPAID("Thanh toán trước");

    private final String displayName;

    public static Map<String, String> getAllDisplayNames() {
        return Arrays.stream(PaymentTermType.values())
                .collect(Collectors.toMap(PaymentTermType::name, PaymentTermType::getDisplayName));
    }
}
