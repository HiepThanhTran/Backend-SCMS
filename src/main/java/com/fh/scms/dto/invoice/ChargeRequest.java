package com.fh.scms.dto.invoice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChargeRequest {

    @NotNull(message = "Tổng tiền không được để trống")
    private BigDecimal amount;

    @NotNull(message = "Thông tin khách hàng không được để trống")
    private Customer customer;

    @NotNull(message = "Danh sách sản phẩm không được để trống")
    private List<Product> products;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Customer {

        @NotNull(message = "Tên khách hàng không được để trống")
        private String customerName;

        @NotNull(message = "Email khách hàng không được để trống")
        private String customerEmail;

        @NotNull(message = "Số điện thoại khách hàng không được để trống")
        private String customerPhone;

        @NotNull(message = "Địa chỉ khách hàng không được để trống")
        private String customerAddress;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Product {

        @NotNull(message = "Tên sản phẩm không được để trống")
        private Long id;

        @NotNull(message = "Tên sản phẩm không được để trống")
        private String name;

        @NotNull(message = "Giá sản phẩm không được để trống")
        private BigDecimal price;
    }
}
