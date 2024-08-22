package com.fh.scm.dto.api.order;

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
public class OrderDetailsReponse {

    private Long id;

    private Float quantity;

    private BigDecimal unitPrice;
    
    private ProductResponse product;
}
