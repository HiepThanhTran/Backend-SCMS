package com.fh.scm.services;

import com.fh.scm.pojo.Rating;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface RatingService {

    Rating get(UUID id);

    void insert(Rating rating);

    void update(Rating rating);

    void delete(UUID id);

    void softDelete(UUID id);

    void insertOrUpdate(Rating rating);

    Long count();

    Boolean exists(UUID id);

    List<Rating> getAll(Map<String, String> params);
}
