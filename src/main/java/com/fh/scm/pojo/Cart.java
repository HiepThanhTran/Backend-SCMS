package com.fh.scm.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cart {

    private Long id;

    private String name;

    private int quantity;

    private Long unitPrice;
}
