package com.fh.scm.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;
import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "unit")
public class Unit extends _BaseEntity implements Serializable {

    @NotNull(message = "{unit.name.notNull}")
    @NotBlank(message = "Tên đơn vị sản phẩm không được rỗng")
    @Column(nullable = false, unique = true)
    private String name;

    @JsonIgnore
    @ManyToMany(mappedBy = "unitSet")
    private Set<Product> productSet;

    @Override
    public String toString() {
        return "com.fh.scm.pojo.Unit[ id=" + this.id + " ]";
    }
}
