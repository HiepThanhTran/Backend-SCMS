package com.fh.scm.services.implement;

import com.fh.scm.pojo.Product;
import com.fh.scm.repository.ProductRepository;
import com.fh.scm.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional
public class ProductServiceImplement implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product get(UUID id) {
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
    public void delete(UUID id) {
        this.productRepository.delete(id);
    }

    @Override
    public void softDelete(UUID id) {
        this.productRepository.softDelete(id);
    }

    @Override
    public void insertOrUpdate(Product Product) {
        this.productRepository.insertOrUpdate(Product);
    }

    @Override
    public Long count() {
        return this.productRepository.count();
    }

    @Override
    public Boolean exists(UUID id) {
        return this.productRepository.exists(id);
    }

    @Override
    public List<Product> getAll(Map<String, String> params) {
        return this.productRepository.getAll(params);
    }
}
