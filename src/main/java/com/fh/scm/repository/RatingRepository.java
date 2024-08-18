package com.fh.scm.repository;

import com.fh.scm.pojo.Rating;

import java.util.List;
import java.util.Map;

public interface RatingRepository {

    Rating get(Long id);

    void insert(Rating rating);

    void update(Rating rating);

    void delete(Long id);

    void softDelete(Long id);

    void insertOrUpdate(Rating rating);

    Long count();

    Boolean exists(Long id);

    List<Rating> getAll(Map<String, String> params);
}
