package com.fh.scm.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
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
@Table(name = "category")
public class Category extends _BaseEntity implements Serializable {

    @NotNull(message = "{category.name.notNull}")
    @NotBlank(message = "{category.name.notNull}")
    @Column(nullable = false, unique = true)
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