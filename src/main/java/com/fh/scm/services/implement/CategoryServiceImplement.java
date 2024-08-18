package com.fh.scm.services.implement;

import com.fh.scm.pojo.Category;
import com.fh.scm.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CategoryServiceImplement implements CategoryService {

    @Autowired
    private CategoryService categoryService;

    @Override
    public Category get(Long id) {
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
    public void delete(Long id) {
        this.categoryService.delete(id);
    }

    @Override
    public void softDelete(Long id) {
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
    public Boolean exists(Long id) {
        return this.categoryService.exists(id);
    }

    @Override
    public List<Category> getAll(Map<String, String> params) {
        return this.categoryService.getAll(params);
    }
}
