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
@Table(name = "category")
public class Category extends _BaseEntity implements Serializable {

    @Column(nullable = false, unique = true)
    @NotNull(message = "{category.name.notNull}")
    private String name;

    private String description;

    @JsonIgnore
    @OneToMany(mappedBy = "category", cascade = {CascadeType.PERSIST})
    private Set<Product> productSet;

    @Override
    public String toString() {
        return "com.fh.scm.pojo.Cateogry[ id=" + this.id + " ]";
    }
}
