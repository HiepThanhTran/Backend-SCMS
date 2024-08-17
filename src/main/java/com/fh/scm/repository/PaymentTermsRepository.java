package com.fh.scm.repository;

import com.fh.scm.pojo.Category;
import com.fh.scm.pojo.PaymentTerms;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface PaymentTermsRepository {

    PaymentTerms get(UUID id);

    void insert(PaymentTerms paymentTerms);

    void update(PaymentTerms paymentTerms);

    void delete(UUID id);

    void softDelete(UUID id);

    void insertOrUpdate(PaymentTerms paymentTerms);

    Long count();

    Boolean exists(UUID id);

    List<PaymentTerms> getAll(Map<String, String> params);
}
