package com.fh.scm.services.implement;

import com.fh.scm.pojo.Rating;
import com.fh.scm.repository.RatingRepository;
import com.fh.scm.services.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class RatingServiceImplement implements RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    @Override
    public Rating get(Long id) {
        return this.ratingRepository.get(id);
    }
    
    @Override
    public Rating getByUserAndSupplier(Long userId, Long supplierId) {
        return this.ratingRepository.getByUserAndSupplier(userId, supplierId);
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
    public void delete(Long id) {
        this.ratingRepository.delete(id);
    }

    @Override
    public void softDelete(Long id) {
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
    public List<Rating> getAll(Map<String, String> params) {
        return this.ratingRepository.getAll(params);
    }
}
