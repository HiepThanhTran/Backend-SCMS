package com.fh.scm.repository;

import com.fh.scm.pojo.Product;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface ProductRepository {

    Product get(UUID id);

    void insert(Product Product);

    void update(Product Product);

    void delete(UUID id);

    void softDelete(UUID id);

    void insertOrUpdate(Product Product);

    Long count();

    Boolean exists(UUID id);

    List<Product> getAll(Map<String, String> params);
}
