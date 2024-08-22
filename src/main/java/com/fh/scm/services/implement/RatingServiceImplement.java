package com.fh.scm.services.implement;

import com.fh.scm.dto.api.rating.RatingRequestUpdate;
import com.fh.scm.pojo.Rating;
import com.fh.scm.repository.RatingRepository;
import com.fh.scm.services.RatingService;
import com.fh.scm.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
@Transactional
public class RatingServiceImplement implements RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    @Override
    public Rating update(Rating rating, RatingRequestUpdate ratingRequestUpdate) {
        Field[] fields = RatingRequestUpdate.class.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(ratingRequestUpdate);

                if (value != null && !value.toString().isEmpty()) {
                    Field ratingField = Rating.class.getDeclaredField(field.getName());
                    ratingField.setAccessible(true);
                    Object convertedValue = Utils.convertValue(ratingField.getType(), value.toString());
                    ratingField.set(rating, convertedValue);
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                Logger.getLogger(UserServiceImplement.class.getName()).log(Level.SEVERE, null, e);
            }
        }

        this.ratingRepository.update(rating);

        return rating;
    }

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
    public Long count() {
        return this.ratingRepository.count();
    }

    @Override
    public List<Rating> getAll(Map<String, String> params) {
        return this.ratingRepository.getAll(params);
    }
}
