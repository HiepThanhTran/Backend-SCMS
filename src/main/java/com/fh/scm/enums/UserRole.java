package com.fh.scm.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@Getter
@AllArgsConstructor
public enum UserRole {

    ROLE_ADMIN("Quản trị viên"),
    ROLE_CUSTOMER("Khách hàng"),
    ROLE_SUPPLIER("Nhà cung cấp"),
    ROLE_DISTRIBUTOR("Nhà phân phối"),
    ROLE_MANUFACTURER("Nhà sản xuất"),
    ROLE_SHIPPER("Nhà vận chuyển");

    private final String displayName;

    public @NotNull String alias() {
        return this.name().substring(5);
    }
}
