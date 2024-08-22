package com.fh.scm.dto.api.rating;

import com.fh.scm.enums.CriteriaType;
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
public class RatingRequestCreate {

    @NotNull(message = "{rating.notNull}")
    @DecimalMin(value = "1.00", message = "{rating.min}")
    @DecimalMax(value = "5.00", message = "{rating.max}")
    private BigDecimal rating = BigDecimal.valueOf(5);

    private String content;

    @NotNull(message = "{rating.criteria.notNull}")
    private CriteriaType criteria;
}
