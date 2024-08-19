package com.fh.scm.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "unit")
public class Unit extends BaseEntity implements Serializable {

    @NotNull(message = "{unit.name.notNull}")
    @Column(nullable = false, unique = true)
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "unit", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private Set<ProductUnit> productSet;
}
