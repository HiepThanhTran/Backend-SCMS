package com.fh.scms.dto.invoice;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fh.scms.dto.tax.TaxResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

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
    private Date invoiceDate;

    private TaxResponse tax;
}
