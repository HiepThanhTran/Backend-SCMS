package com.fh.scm.services.implement;

import com.fh.scm.dto.api.product.ProductResponse;
import com.fh.scm.pojo.Product;
import com.fh.scm.repository.ProductRepository;
import com.fh.scm.services.CategoryService;
import com.fh.scm.services.ProductService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ProductServiceImplement implements ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryService categoryService;

    public ProductResponse getProductResponse(@NotNull Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .image(product.getImage())
                .expiryDate(product.getExpiryDate())
                .category(this.categoryService.getCategoryResponse(product.getCategory()))
                .build();
    }

    @Override
    public Product get(Long id) {
        return this.productRepository.get(id);
    }

    @Override
    public void insert(Product Product) {
        this.productRepository.insert(Product);
    }

    @Override
    public void update(Product Product) {
        this.productRepository.update(Product);
    }

    @Override
    public void delete(Long id) {
        this.productRepository.delete(id);
    }

    @Override
    public void softDelete(Long id) {
        this.productRepository.softDelete(id);
    }

    @Override
    public Long count() {
        return this.productRepository.count();
    }

    @Override
    public List<Product> getAll(Map<String, String> params) {
        return this.productRepository.getAll(params);
    }
}
