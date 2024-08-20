package com.fh.scm.services;

import com.fh.scm.pojo.Rating;

import java.util.List;
import java.util.Map;

public interface RatingService {

    Rating get(Long id);

    Rating getByUserAndSupplier(Long userId, Long supplierId);

    void insert(Rating rating);

    void update(Rating rating);

    void delete(Long id);

    void softDelete(Long id);

    void insertOrUpdate(Rating rating);

    Long count();

    List<Rating> getAll(Map<String, String> params);
}
