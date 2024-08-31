package com.fh.scms.pojo;

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
@Table(name = "tag")
public class Tag extends BaseEntity implements Serializable {

    @NotNull(message = "{tag.name.notNull}")
    @NotBlank(message = "{tag.name.notNull}")
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

    @PreRemove
    private void removeTagFromProduct() {
        for (Product product : this.productTagSet) {
            product.getTagSet().remove(this);
        }
    }
}
