package com.fh.scms.dto.statistics;

import com.fh.scms.enums.CriteriaType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SupplierPerformanceReport {

    private List<RatingMonth> cost;

    private List<RatingMonth> quality;

    private List<RatingMonth> timelyDelivery;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RatingMonth {

        private Integer month;

        private Double averageRating;
    }
}
