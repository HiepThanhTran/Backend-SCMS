package com.fh.scms.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cart")
public class Cart extends BaseEntity implements Serializable {

    @JsonIgnore
    @OneToOne(cascade = {CascadeType.REMOVE}, optional = false, orphanRemoval = true)
    private User user;

    @OneToMany(mappedBy = "cart", fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private Set<CartDetails> cartDetailsSet;
}
