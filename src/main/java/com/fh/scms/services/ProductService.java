package com.fh.scms.services;

import com.fh.scms.dto.product.ProductResponse;
import com.fh.scms.dto.product.ProductResponseWithTagUnit;
import com.fh.scms.pojo.Product;

import java.util.List;
import java.util.Map;

public interface ProductService {

    ProductResponse getProductResponse(Product product);

    ProductResponseWithTagUnit getProductResponseWithTagUnit(Product product);

    List<ProductResponseWithTagUnit> getAllProductResponseWithTagUnit(Map<String, String> params);

    Product findById(Long id);

    void save(Product Product);

    void save(Product product, List<String> tagIds, List<String> unitIds);

    void update(Product Product);

    void update(Product product, List<String> tagIds, List<String> unitIds);

    void delete(Long id);

    Long count();

    List<Product> findAllWithFilter(Map<String, String> params);
}
