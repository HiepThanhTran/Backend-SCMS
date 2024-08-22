package com.fh.scm.util;

import org.jetbrains.annotations.NotNull;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public final class Utils {

    public static Object convertValue(Class<?> fieldType, String value) {
        if (fieldType == String.class) {
            return value;
        } else if (fieldType == int.class || fieldType == Integer.class) {
            return Integer.parseInt(value);
        } else if (fieldType == long.class || fieldType == Long.class) {
            return Long.parseLong(value);
        } else if (fieldType == float.class || fieldType == Float.class) {
            return Float.parseFloat(value);
        }else if (fieldType == double.class || fieldType == Double.class) {
            return Double.parseDouble(value);
        } else if (fieldType == boolean.class || fieldType == Boolean.class) {
            return Boolean.parseBoolean(value);
        } else if (fieldType == LocalDateTime.class) {
            return parseDate(value);
        } else {
            throw new IllegalArgumentException("Không hỗ trợ kiểu dữ liệu: " + fieldType.getName());
        }
    }

    public static Boolean parseBoolean(String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }

        value = value.trim().toLowerCase();
        if ("true".equalsIgnoreCase(value)) {
            return true;
        } else if ("false".equalsIgnoreCase(value)) {
            return false;
        }

        return null;
    }

    public static LocalDateTime parseDate(String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }

        try {
            return LocalDateTime.parse(value, Constants.DATE_FORMATTER);
        } catch (Exception e) {
            LoggerFactory.getLogger(Utils.class).error("An error parse LocalDateTime", e);
            return null;
        }
    }

    public static @NotNull Map<String, String> generateMappingPojoClass() {
        Map<String, String> mapping = new HashMap<>();

        mapping.put("categories", "Danh mục");
        mapping.put("customers", "Khách hàng");
//        mapping.add("Lịch giao hàng");
//        mapping.add("Tồn kho");
//        mapping.add("Chi tiết tồn kho");
//        mapping.add("Hóa đơn");
//        mapping.add("Đơn hàng");
//        mapping.add("Chi tiết đơn hàng");
//        mapping.add("Phương thức thanh toán");
//        mapping.add("Sản phẩm");
//        mapping.add("Đánh giá");
//        mapping.add("Vận chuyển");
//        mapping.add("Người giao hàng");
//        mapping.add("Nhà cung cấp");
//        mapping.add("Chi phí nhà cung cấp");
//        mapping.add("Tag");
//        mapping.add("Thuế");
//        mapping.add("Đơn vị");
//        mapping.add("Người dùng");
//        mapping.add("Kho");

        return mapping;
    }
}
