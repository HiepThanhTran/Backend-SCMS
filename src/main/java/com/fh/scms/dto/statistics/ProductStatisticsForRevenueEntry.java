package com.fh.scms.dto.statistics;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class ProductStatisticsForRevenueEntry {

    private Long productId;

    private String productName;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private Date time;

    private BigDecimal revenue;
}
