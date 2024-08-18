package com.fh.scm.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {

    ADMIN("Quản trị viên"),
    CUSTOMER("Khách hàng"),
    SUPPLIER("Nhà cung cấp"),
    DISTRIBUTOR("Nhà phân phối"),
    MANUFACTURER("Nhà sản xuất"),
    SHIPPER("Nhà vận chuyển");

    private final String displayName;
}
