package com.fh.scm.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderType {

    INBOUND("Nhập kho"),
    OUTBOUND("Xuất kho");

    private final String displayName;
}
