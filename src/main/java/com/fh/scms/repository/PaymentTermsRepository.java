package com.fh.scms.repository;

import com.fh.scms.pojo.PaymentTerms;

import java.util.List;
import java.util.Map;

public interface PaymentTermsRepository {

    PaymentTerms get(Long id);

    void insert(PaymentTerms paymentTerms);

    void update(PaymentTerms paymentTerms);

    void delete(Long id);

    void softDelete(Long id);

    Long count();

    List<PaymentTerms> getAll(Map<String, String> params);
}
