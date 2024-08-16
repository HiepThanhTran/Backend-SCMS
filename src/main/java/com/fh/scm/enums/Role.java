package com.fh.scm.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {

    ADMIN("Quản trị viên"),
    SUPPLIER("Nhà cung cấp"),
    SHIPPER("Nhà vận chuyển"),
    CUSTOMER("Khách hàng"),
    DISTRIBUTOR("Đại lý"),
    MANUFACTURER("Nhà sản xuất");

    private final String displayName;
}
