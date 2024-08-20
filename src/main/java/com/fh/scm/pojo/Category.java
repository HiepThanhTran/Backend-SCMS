package com.fh.scm.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "category")
public class Category extends BaseEntity implements Serializable {

    @Column(nullable = false, unique = true)
    @NotNull(message = "{category.name.notNull}")
    @NotBlank(message = "Tên danh mục không được rỗng")
    private String name;

    private String description;

    @JsonIgnore
    @OneToMany(mappedBy = "category", cascade = {CascadeType.PERSIST})
    private Set<Product> productSet;
}
