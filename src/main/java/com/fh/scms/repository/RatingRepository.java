package com.fh.scms.repository;

import com.fh.scms.enums.CriteriaType;
import com.fh.scms.pojo.Rating;
import com.fh.scms.pojo.Supplier;

import java.util.List;
import java.util.Map;

public interface RatingRepository {

    Rating findById(Long id);

    Rating findByUserIdAndSupplierId(Long userId, Long supplierId);

    void save(Rating rating);

    void update(Rating rating);

    void delete(Long id);

    Long count();

    List<Rating> findAllWithFilter(Map<String, String> params);

    List<Supplier> getRankedSuppliers(CriteriaType criteriaType, String sortOrder);
}
