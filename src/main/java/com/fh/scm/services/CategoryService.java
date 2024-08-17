package com.fh.scm.services;

import com.fh.scm.pojo.Category;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface CategoryService {

    Category get(UUID id);

    void insert(Category category);

    void update(Category category);

    void delete(UUID id);

    void softDelete(UUID id);

    void insertOrUpdate(Category category);

    Long count();

    Boolean exists(UUID id);

    List<Category> getAll(Map<String, String> params);
}
