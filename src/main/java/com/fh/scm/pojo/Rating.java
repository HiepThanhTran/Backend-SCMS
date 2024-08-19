package com.fh.scm.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fh.scm.enums.CriteriaType;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "rating")
public class Rating extends BaseEntity implements Serializable {

    @Builder.Default
    @DecimalMin(value = "1.00", message = "{rating.min}")
    @DecimalMax(value = "5.00", message = "{rating.max}")
    @Column(nullable = false, precision = 2, scale = 1, columnDefinition = "decimal default 0.0")
    private Float rating = 0.0f;

    private String content;

    @Builder.Default
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull(message = "{rating.criteria.notNull}")
    private CriteriaType criteria = CriteriaType.QUALITY;

    @JsonIgnore
    @ManyToOne(optional = false)
    @JoinColumn(name = "supplier_id", nullable = false)
    private Supplier supplier;
}
