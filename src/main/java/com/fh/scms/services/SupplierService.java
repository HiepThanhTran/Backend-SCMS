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

    Supplier get(Long id);

    Supplier getByUser(User user);

    Supplier getByPhone(String phone);

    void insert(Supplier supplier);

    void update(Supplier supplier);

    void delete(Long id);

    void softDelete(Long id);

    Long count();

    List<Supplier> getAll(Map<String, String> params);
}
