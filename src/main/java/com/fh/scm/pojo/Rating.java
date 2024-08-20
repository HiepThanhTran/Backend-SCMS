package com.fh.scm.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fh.scm.enums.CriteriaType;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "rating", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"supplier_id", "user_id"})
})
public class Rating extends _BaseEntity implements Serializable {

    @Builder.Default
    @NotNull(message = "{rating.notNull}")
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

    @ManyToOne(optional = false)
    @JoinColumn(name = "supplier_id", nullable = false)
    private Supplier supplier;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Override
    public String toString() {
        return "com.fh.scm.pojo.Rating[ id=" + this.id + " ]";
    }
}
