package com.fh.scm.dto.api.order;

import com.fh.scm.dto.api.product.ProductResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailsRequest {

    @NotNull
    private Long productId;

    private Float quantity;

    private BigDecimal unitPrice;
}
