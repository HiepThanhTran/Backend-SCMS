package com.fh.scm.dto.api.cart;

import com.fh.scm.dto.api.product.ProductResponse;
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

    private ProductResponse product;
}
