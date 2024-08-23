package com.fh.scms.services;

import com.fh.scms.pojo.PaymentTerms;

import java.util.List;
import java.util.Map;

public interface PaymentTermsService {

    PaymentTerms get(Long id);

    void insert(PaymentTerms paymentTerms);

    void update(PaymentTerms paymentTerms);

    void delete(Long id);

    void softDelete(Long id);

    Long count();

    List<PaymentTerms> getAll(Map<String, String> params);
}
