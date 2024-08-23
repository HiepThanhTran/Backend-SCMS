package com.fh.scms.repository;

import com.fh.scms.pojo.Rating;

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
