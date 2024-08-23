package com.fh.scms.repository;

import com.fh.scms.pojo.Category;

import java.util.List;
import java.util.Map;

public interface CategoryRepository {

    Category findById(Long id);

    void save(Category category);

    void update(Category category);

    void delete(Long id);

    Long count();

    List<Category> findAllWithFilter(Map<String, String> params);
}
