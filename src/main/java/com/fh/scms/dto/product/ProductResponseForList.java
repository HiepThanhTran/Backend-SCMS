package com.fh.scms.dto.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseForList {

    private Long id;

    private String name;

    private String description;

    private BigDecimal price;

    private String image;
}
