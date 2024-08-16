package com.fh.scm.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PaymentTermType {

    EOM("Thanh toán vào cuối tháng"),
    COD("Thanh toán khi nhận hàng"),
    PREPAID("Thanh toán trước");

    private final String displayName;
}
