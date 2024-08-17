package com.fh.scm.services.implement;

import com.fh.scm.pojo.Category;
import com.fh.scm.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional
public class CategoryServiceImplement implements CategoryService {

    @Autowired
    private CategoryService categoryService;

    @Override
    public Category get(UUID id) {
        return this.categoryService.get(id);
    }

    @Override
    public void insert(Category category) {
        this.categoryService.insert(category);
    }

    @Override
    public void update(Category category) {
        this.categoryService.update(category);
    }

    @Override
    public void delete(UUID id) {
        this.categoryService.delete(id);
    }

    @Override
    public void softDelete(UUID id) {
        this.categoryService.softDelete(id);
    }

    @Override
    public void insertOrUpdate(Category category) {
        this.categoryService.insertOrUpdate(category);
    }

    @Override
    public Long count() {
        return this.categoryService.count();
    }

    @Override
    public Boolean exists(UUID id) {
        return this.categoryService.exists(id);
    }

    @Override
    public List<Category> getAll(Map<String, String> params) {
        return this.categoryService.getAll(params);
    }
}
