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
@Table(name = "tag")
public class Tag extends BaseEntity implements Serializable {

    @NotNull(message = "{tag.name.notNull}")
    @Column(nullable = false, unique = true)
    private Integer name;

    private String description;

    @JsonIgnore
    @OneToMany(mappedBy = "tag", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private Set<ProductTag> productTagSet;
}
