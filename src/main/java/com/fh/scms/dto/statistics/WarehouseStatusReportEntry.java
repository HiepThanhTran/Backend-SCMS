package com.fh.scms.dto.statistics;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseStatusReportEntry {

    private Long warehouseId;

    private String warehouseName;

    private Float warehouseCapacity;

    private Double remainingCapacity;
}
