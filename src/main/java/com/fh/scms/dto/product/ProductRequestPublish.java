package com.fh.scms.dto.product;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestPublish {

    MultipartFile image;

    @NotNull(message = "{product.name.notNull}")
    private String name;

    private String description;

    @NotNull(message = "{product.price.notNull}")
    private BigDecimal price;

    @NotNull(message = "{product.expiryDate.notNull}")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate expiryDate;

    private Long unit;

    private Long category;

    private Set<Long> tags;
}
