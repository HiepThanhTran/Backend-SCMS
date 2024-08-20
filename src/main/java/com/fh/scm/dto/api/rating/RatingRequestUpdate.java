package com.fh.scm.dto.api.rating;

import com.fh.scm.enums.CriteriaType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.validation.constraints.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RatingRequestUpdate {

    @DecimalMin(value = "1.00", message = "{rating.min}")
    @DecimalMax(value = "5.00", message = "{rating.max}")
    private Float rating;

    private String content;

    private CriteriaType criteria;
}
