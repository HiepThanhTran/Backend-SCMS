package com.fh.scms.services;

import com.fh.scms.dto.product.ProductResponse;
import com.fh.scms.pojo.Product;

import java.util.List;
import java.util.Map;

public interface ProductService {

    ProductResponse getProductResponse(Product product);

    Product get(Long id);

    void insert(Product Product);

    void insert(Product product, List<String> tagIds, List<String> unitIds);

    void update(Product Product);

    void update(Product product, List<String> tagIds, List<String> unitIds);

    void delete(Long id);

    void softDelete(Long id);

    Long count();

    List<Product> getAll(Map<String, String> params);
}
