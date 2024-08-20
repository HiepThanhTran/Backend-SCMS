package com.fh.scm.services;

import com.fh.scm.dto.api.rating.RatingRequest;
import com.fh.scm.dto.api.supplier.PaymentTermsRequest;
import com.fh.scm.dto.api.supplier.SupplierDTO;
import com.fh.scm.pojo.PaymentTerms;
import com.fh.scm.pojo.Rating;
import com.fh.scm.pojo.Supplier;
import com.fh.scm.pojo.User;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface SupplierService {

    SupplierDTO getSupplierResponse(Supplier supplier);

    Supplier getProfileSupplier(String username);

    SupplierDTO updateProfileSupplier(String username, SupplierDTO supplierDTO);

    List<PaymentTerms> getAllPaymentTermsOfSupplier(String username);

    void addPaymentTermsForSupplier(Long supplierId, PaymentTermsRequest paymentTermsIdList);

    void addRatingForSupplier(String username, Long supplierId, RatingRequest ratingRequest);

    Supplier get(Long id);

    Supplier getByUser(User user);

    Supplier getByPhone(String phone);

    void insert(Supplier supplier);

    void update(Supplier supplier);

    void delete(Long id);

    void softDelete(Long id);

    void insertOrUpdate(Supplier supplier);

    Long count();

    List<Supplier> getAll(Map<String, String> params);
}
