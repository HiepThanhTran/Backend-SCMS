package com.fh.scm.dto.api.payment_temrs;

import com.fh.scm.enums.PaymentTermType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentTermsRequest {

    @NotNull(message = "{paymentTerms.discountDays.notNull}")
    private Integer discountDays; // Số ngày hưởng chiết khấu

    @DecimalMin(value = "0.01", message = "{paymentTerms.discountPercentage.min}")
    @DecimalMax(value = "1.00", message = "{paymentTerms.discountPercentage.max}")
    private Float discountPercentage; // Phần trăm chiết khấu (nếu có)

    @NotNull(message = "{paymentTerms.termType.notNull}")
    private PaymentTermType type;
}
