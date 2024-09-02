package com.fh.scms.services;

import com.fh.scms.dto.product.ProductResponseForDetails;
import com.fh.scms.dto.product.ProductResponseForList;
import com.fh.scms.pojo.Product;

import java.util.List;
import java.util.Map;

public interface ProductService {

    Product findById(Long id);

    void save(Product Product);

    void update(Product Product);

    void delete(Long id);

    Long count();

    List<Product> findAllWithFilter(Map<String, String> params);

    ProductResponseForList getProductResponseForList(Product product);

    List<ProductResponseForList> getAllProductResponseForList(List<Product> products);

    ProductResponseForDetails getProductResponseForDetails(Product product);

    void save(Product product, List<Long> tagIds);

    void update(Product product, List<Long> tagIds);
}
