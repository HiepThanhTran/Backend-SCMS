package com.fh.scm.services.implement;

import com.fh.scm.pojo.Rating;
import com.fh.scm.repository.RatingRepository;
import com.fh.scm.services.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional
public class RatingServiceImplement implements RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    @Override
    public Rating get(UUID id) {
        return this.ratingRepository.get(id);
    }

    @Override
    public void insert(Rating rating) {
        this.ratingRepository.insert(rating);
    }

    @Override
    public void update(Rating rating) {
        this.ratingRepository.update(rating);
    }

    @Override
    public void delete(UUID id) {
        this.ratingRepository.delete(id);
    }

    @Override
    public void softDelete(UUID id) {
        this.ratingRepository.softDelete(id);
    }

    @Override
    public void insertOrUpdate(Rating rating) {
        this.ratingRepository.insertOrUpdate(rating);
    }

    @Override
    public Long count() {
        return this.ratingRepository.count();
    }

    @Override
    public Boolean exists(UUID id) {
        return this.ratingRepository.exists(id);
    }

    @Override
    public List<Rating> getAll(Map<String, String> params) {
        return this.ratingRepository.getAll(params);
    }
}
