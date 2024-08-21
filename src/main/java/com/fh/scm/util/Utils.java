package com.fh.scm.util;

import com.cloudinary.Cloudinary;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Utils {

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

    public static Map<String, String> generateMappingPojoClass() {
        Map<String, String> mapping = new HashMap<>();

        mapping.put("categories", "Danh mục");
        mapping.put("customers", "Khách hàng");
        mapping.put("delivery-schedules","Lịch giao hàng");
        mapping.put("inventories","Tồn kho");
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
        mapping.put("tags","Nhãn");
        mapping.put("taxs","Thuế");
        mapping.put("units","Đơn vị");
//        mapping.add("Người dùng");
        mapping.put("warehouses","Kho");

        return mapping;
    }
}
