package com.fh.scms.services;

import com.fh.scms.dto.rating.RatingRequestCreate;
import com.fh.scms.dto.supplier.SupplierDTO;
import com.fh.scms.pojo.Rating;
import com.fh.scms.pojo.Supplier;
import com.fh.scms.pojo.User;

import java.util.List;
import java.util.Map;

public interface SupplierService {

    SupplierDTO getSupplierResponse(Supplier supplier);

    Supplier getProfileSupplier(String username);

    SupplierDTO updateProfileSupplier(String username, SupplierDTO supplierDTO);

    Rating addRatingForSupplier(String username, Long supplierId, RatingRequestCreate ratingRequestCreate);

    Supplier findById(Long id);

    Supplier findByUser(User user);

    Supplier findByPhone(String phone);

    void save(Supplier supplier);

    void update(Supplier supplier);

    void delete(Long id);

    Long count();

    List<Supplier> findAllWithFilter(Map<String, String> params);
}
