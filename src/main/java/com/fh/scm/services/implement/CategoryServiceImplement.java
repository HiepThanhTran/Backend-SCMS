package com.fh.scm.services.implement;

import com.fh.scm.pojo.Category;
import com.fh.scm.repository.CategoryRepository;
import com.fh.scm.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CategoryServiceImplement implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category get(Long id) {
        return this.categoryRepository.get(id);
    }

    @Override
    public void insert(Category category) {
        this.categoryRepository.insert(category);
    }

    @Override
    public void update(Category category) {
        this.categoryRepository.update(category);
    }

    @Override
    public void delete(Long id) {
        this.categoryRepository.delete(id);
    }

    @Override
    public void softDelete(Long id) {
        this.categoryRepository.softDelete(id);
    }

    @Override
    public void insertOrUpdate(Category category) {
        this.categoryRepository.insertOrUpdate(category);
    }

    @Override
    public Long count() {
        return this.categoryRepository.count();
    }

    @Override
    public List<Category> getAll(Map<String, String> params) {
        return this.categoryRepository.getAll(params);
    }
}
