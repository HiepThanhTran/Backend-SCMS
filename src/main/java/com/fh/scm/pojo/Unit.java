package com.fh.scm.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "unit")
public class Unit extends _BaseEntity implements Serializable {

    @NotNull(message = "{unit.name.notNull}")
    @NotBlank(message = "{unit.name.notNull}")
    @Column(nullable = false, unique = true)
    private String name;

    @NotNull(message = "{unit.abbreviation.notNull}")
    @NotBlank(message = "{unit.abbreviation.notNull}")
    @Column(nullable = false, unique = true)
    private String abbreviation;

    @JsonIgnore
    @ManyToMany(mappedBy = "unitSet")
    private Set<Product> productSet;

    @Override
    public String toString() {
        return "com.fh.scm.pojo.Unit[ id=" + this.id + " ]";
    }
}
