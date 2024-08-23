package com.fh.scms.util;

import org.jetbrains.annotations.NotNull;
import org.slf4j.LoggerFactory;

import java.text.Collator;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

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
        } else if (fieldType == double.class || fieldType == Double.class) {
            return Double.parseDouble(value);
        } else if (fieldType == boolean.class || fieldType == Boolean.class) {
            return Boolean.parseBoolean(value);
        } else if (fieldType == LocalDateTime.class) {
            return parseLocalDateTime(value);
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

    public static LocalDateTime parseLocalDateTime(String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }

        try {
            return LocalDateTime.parse(value, Constants.DATE_TIME_FORMATTER);
        } catch (Exception e) {
            LoggerFactory.getLogger(Utils.class).error("An error parse LocalDateTime", e);
            return null;
        }
    }

    public static Date parseDate(String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }

        try {
            return Date.from(LocalDateTime.parse(value, Constants.DATE_TIME_FORMATTER).atZone(ZoneId.systemDefault()).toInstant());
        } catch (Exception e) {
            LoggerFactory.getLogger(Utils.class).error("An error parse Date", e);
            return null;
        }
    }

    public static @NotNull List<Map.Entry<String, String>> generateMappingPojoClass() {
        Map<String, String> mapping = new HashMap<>();

        mapping.put("categories", "Danh mục");
        mapping.put("customers", "Khách hàng");
        mapping.put("delivery-schedules", "Lịch giao hàng");
        mapping.put("inventories", "Tồn kho");
        mapping.put("invoices", "Hóa đơn");
        mapping.put("orders", "Đơn hàng");
        mapping.put("payment-terms", "Phương thức thanh toán");
        mapping.put("products", "Sản phẩm");
        mapping.put("ratings", "Đánh giá");
        mapping.put("shipments", "Đơn vận chuyển");
        mapping.put("shippers", "Nhà vận chuyển");
        mapping.put("suppliers", "Nhà cung cấp");
        mapping.put("tags", "Nhãn");
        mapping.put("taxs", "Thuế");
        mapping.put("units", "Đơn vị");
        mapping.put("users", "Người dùng");
        mapping.put("warehouses", "Nhà kho");

        Collator collator = Collator.getInstance(new Locale("vi", "VN"));
        return mapping.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(collator))
                .collect(Collectors.toList());
    }
}
