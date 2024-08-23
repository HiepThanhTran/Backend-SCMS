package com.fh.scms.services;

import com.fh.scms.dto.rating.RatingRequestUpdate;
import com.fh.scms.pojo.Rating;

import java.util.List;
import java.util.Map;

public interface RatingService {

    Rating update(Rating rating, RatingRequestUpdate ratingRequestUpdate);

    Rating get(Long id);

    Rating getByUserAndSupplier(Long userId, Long supplierId);

    void insert(Rating rating);

    void update(Rating rating);

    void delete(Long id);

    void softDelete(Long id);

    Long count();

    List<Rating> getAll(Map<String, String> params);
}
