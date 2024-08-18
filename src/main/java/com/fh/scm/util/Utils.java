package com.fh.scm.util;

import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public final class Utils {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static Boolean parseBoolean(String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }

        value = value.trim().toLowerCase();
        if ("true".equals(value)) {
            return true;
        } else if ("false".equals(value)) {
            return false;
        }

        return null;
    }

    public static LocalDateTime parseDate(String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }

        try {
            return LocalDateTime.parse(value, DATE_FORMATTER);
        } catch (Exception e) {
            LoggerFactory.getLogger(Utils.class).error("An error parse LocalDateTime", e);
            return null;
        }
    }

    public static List<String> generateMappingPojoClass() {
        List<String> mapping = new ArrayList<>();

        mapping.add("Danh mục");
        mapping.add("Khách hàng");
        mapping.add("Lịch giao hàng");
        mapping.add("Tồn kho");
        mapping.add("Chi tiết tồn kho");
        mapping.add("Hóa đơn");
        mapping.add("Đơn hàng");
        mapping.add("Chi tiết đơn hàng");
        mapping.add("Phương thức thanh toán");
        mapping.add("Sản phẩm");
        mapping.add("Tag sản phẩm");
        mapping.add("Đơn vị sản phẩm");
        mapping.add("Đánh giá");
        mapping.add("Vận chuyển");
        mapping.add("Người giao hàng");
        mapping.add("Nhà cung cấp");
        mapping.add("Chi phí nhà cung cấp");
        mapping.add("Phương thức thanh toán của nhà cung cấp");
        mapping.add("Tag");
        mapping.add("Thuế");
        mapping.add("Đơn vị");
        mapping.add("Người dùng");
        mapping.add("Kho");

        return mapping;
    }
}
