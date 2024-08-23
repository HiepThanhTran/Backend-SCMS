package com.fh.scms.services.implement;

import com.fh.scms.dto.rating.RatingRequestUpdate;
import com.fh.scms.pojo.Rating;
import com.fh.scms.repository.RatingRepository;
import com.fh.scms.services.RatingService;
import com.fh.scms.util.Utils;
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
    public Rating findById(Long id) {
        return this.ratingRepository.findById(id);
    }

    @Override
    public Rating findByUserIdAndSupplierId(Long userId, Long supplierId) {
        return this.ratingRepository.findByUserIdAndSupplierId(userId, supplierId);
    }

    @Override
    public void save(Rating rating) {
        this.ratingRepository.save(rating);
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
    public Long count() {
        return this.ratingRepository.count();
    }

    @Override
    public List<Rating> findAllWithFilter(Map<String, String> params) {
        return this.ratingRepository.findAllWithFilter(params);
    }
}
