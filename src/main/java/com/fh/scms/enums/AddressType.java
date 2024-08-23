package com.fh.scms.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public enum AddressType {

    HOME("Nhà riêng"),
    OFFICE("Văn phòng");

    private final String displayName;

    public static Map<String, String> getAllDisplayNames() {
        return Arrays.stream(AddressType.values())
                .collect(Collectors.toMap(AddressType::name, AddressType::getDisplayName));
    }
}
