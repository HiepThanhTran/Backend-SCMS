package com.fh.scm.util;

import org.jetbrains.annotations.NotNull;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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

    public static @NotNull Map<String, String> generateMappingPojoClass(@NotNull Set<Class<?>> classes) {
        Map<String, String> mapping = new HashMap<>();

        for (Class<?> clazz : classes) {
            String vietnameseName = generateVietnameseName(clazz.getSimpleName());
            mapping.put(clazz.getSimpleName(), vietnameseName);
        }

        return mapping;
    }

    private static String generateVietnameseName(@NotNull String className) {
        switch (className) {
            case "Category":
                return "Danh mục";
            case "Customer":
                return "Khách hàng";
            case "DeliverySchedule":
                return "Lịch giao hàng";
            case "Inventory":
                return "Tồn kho";
            case "InventoryDetails":
                return "Chi tiết tồn kho";
            case "Invoice":
                return "Hóa đơn";
            case "Order":
                return "Đơn hàng";
            case "OrderDetails":
                return "Chi tiết đơn hàng";
            case "PaymentTerms":
                return "Phương thức thanh toán";
            case "Product":
                return "Sản phẩm";
            case "ProductTag":
                return "Tag sản phẩm";
            case "ProductUnit":
                return "Đơn vị sản phẩm";
            case "Rating":
                return "Đánh giá";
            case "Shipment":
                return "Vận chuyển";
            case "Shipper":
                return "Người giao hàng";
            case "Supplier":
                return "Nhà cung cấp";
            case "SupplierCosting":
                return "Chi phí nhà cung cấp";
            case "SupplierPaymentTerms":
                return "Phương thức thanh toán của nhà cung cấp";
            case "Tag":
                return "Tag";
            case "Tax":
                return "Thuế";
            case "Unit":
                return "Đơn vị";
            case "User":
                return "Người dùng";
            case "Warehouse":
                return "Kho";
            default:
                return className;
        }
    }
}
