package com.fh.scm.repository;

import com.fh.scm.pojo.Product;

import java.util.List;
import java.util.Map;

public interface ProductRepository {

    Product get(Long id);

    void insert(Product Product);

    void update(Product Product);

    void delete(Long id);

    void softDelete(Long id);

    void insertOrUpdate(Product Product);

    Long count();

    Boolean exists(Long id);

    List<Product> getAll(Map<String, String> params);
}
