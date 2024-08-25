package com.fh.scms.services;

import com.fh.scms.dto.product.ProductResponseForDetails;
import com.fh.scms.dto.product.ProductResponseForList;
import com.fh.scms.pojo.Product;

import java.util.List;
import java.util.Map;

public interface ProductService {

    ProductResponseForList getProductResponseForList(Product product);

    ProductResponseForDetails getProductResponseForDetails(Product product);

    List<ProductResponseForList> getAllProductResponseForList(Map<String, String> params);

    void save(Product product, List<String> tagIds);

    void update(Product product, List<String> tagIds);

    Product findById(Long id);

    void save(Product Product);

    void update(Product Product);

    void delete(Long id);

    Long count();

    List<Product> findAllWithFilter(Map<String, String> params);
}
