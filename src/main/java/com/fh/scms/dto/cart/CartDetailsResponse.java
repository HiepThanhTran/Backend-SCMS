package com.fh.scms.dto.cart;

import com.fh.scms.dto.product.ProductResponseForList;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartDetailsResponse {

    private Long id;

    private Float quantity;

    private BigDecimal unitPrice;

    private ProductResponseForList product;
}
