package com.fh.scms.services;

import com.fh.scms.dto.rating.RatingRequestUpdate;
import com.fh.scms.pojo.Rating;

import java.util.List;
import java.util.Map;

public interface RatingService {

    Rating update(Rating rating, RatingRequestUpdate ratingRequestUpdate);

    Rating findById(Long id);

    void save(Rating rating);

    void update(Rating rating);

    void delete(Long id);

    Long count();

    List<Rating> findAllWithFilter(Map<String, String> params);
}
