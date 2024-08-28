package com.fh.scms.dto.rating;

import com.fh.scms.pojo.Supplier;
import com.fh.scms.pojo.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RatingResponse {

    private Long id;

    private BigDecimal rating;

    private String comment;

    private String criteria;

    private Supplier supplier;

    private User user;
}
