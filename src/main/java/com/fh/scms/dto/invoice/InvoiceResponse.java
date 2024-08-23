package com.fh.scms.dto.invoice;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fh.scms.dto.tax.TaxResponse;
import com.fh.scms.enums.PaymentTermType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceResponse {

    private Long id;

    private String invoiceNumber;

    private Boolean isPaid = false;

    private BigDecimal totalAmount;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime invoiceDate;

    private TaxResponse tax;
}
