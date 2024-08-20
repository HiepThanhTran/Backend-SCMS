package com.fh.scm.repository;

import com.fh.scm.pojo.Rating;

import java.util.List;
import java.util.Map;

public interface RatingRepository {

    Rating get(Long id);

    Rating getByUserAndSupplier(Long userId, Long supplierId);

    void insert(Rating rating);

    void update(Rating rating);

    void delete(Long id);

    void softDelete(Long id);

    Long count();

    List<Rating> getAll(Map<String, String> params);
}
