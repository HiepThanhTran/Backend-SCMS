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
@Table(name = "tag")
public class Tag extends _BaseEntity implements Serializable {

    @NotNull(message = "{tag.name.notNull}")
    @NotBlank(message = "Tên nhãn không được rỗng")
    @Column(nullable = false, unique = true)
    private String name;

    private String description;

    @JsonIgnore
    @ManyToMany(mappedBy = "tagSet")
    private Set<Product> productTagSet;

    @Override
    public String toString() {
        return "com.fh.scm.pojo.Tag[ id=" + this.id + " ]";
    }
}
