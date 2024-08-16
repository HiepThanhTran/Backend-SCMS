package com.fh.scm.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AddressType {

    HOME("Nhà riêng"),
    OFFICE("Văn phòng");

    private final String displayName;
}
