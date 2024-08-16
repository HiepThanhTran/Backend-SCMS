package com.fh.scm.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CriteriaType {

    COST("Giá cả"),
    QUALITY("Chất lượng"),
    TIMELY_DELIVERY("Giao hàng đúng hạn"),
    ;

    private final String displayName;
}
