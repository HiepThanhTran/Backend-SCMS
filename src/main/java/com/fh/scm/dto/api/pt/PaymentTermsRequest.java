package com.fh.scm.dto.api.pt;

import com.fh.scm.enums.PaymentTermType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentTermsRequest {

    @NotNull(message = "{paymentTerms.discountDays.notNull}")
    private Integer discountDays; // Số ngày hưởng chiết khấu

    @DecimalMin(value = "0.01", message = "{paymentTerms.discountPercentage.min}")
    @DecimalMax(value = "1.00", message = "{paymentTerms.discountPercentage.max}")
    private BigDecimal discountPercentage; // Phần trăm chiết khấu (nếu có)

    @NotNull(message = "{paymentTerms.termType.notNull}")
    private PaymentTermType type;
}
