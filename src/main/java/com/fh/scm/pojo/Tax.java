package com.fh.scm.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "tax")
public class Tax extends BaseEntity implements Serializable {

    @Builder.Default
    @DecimalMin(value = "0.01", inclusive = true, message = "...")
    @DecimalMax(value = "1.00", inclusive = true, message = "...")
    @Column(nullable = false, precision = 5, scale = 2, columnDefinition = "float default 0.0")
    private Float rate = 0.0f;

    @Builder.Default
    @Size(min = 1, max = 15, message = "...")
    @Column(nullable = false, unique = true, length = 15, columnDefinition = "varchar(15) default 'VI'")
    private String region = "VI";

    @JsonIgnore
    @OneToMany(mappedBy = "tax", cascade = {CascadeType.PERSIST})
    private Set<Invoice> invoiceSet;
}
