package com.fh.scms.repository;

import com.fh.scms.pojo.PaymentTerms;

import java.util.List;
import java.util.Map;

public interface PaymentTermsRepository {

    PaymentTerms findById(Long id);

    void save(PaymentTerms paymentTerms);

    void update(PaymentTerms paymentTerms);

    void delete(Long id);

    Long count();

    List<PaymentTerms> findAllWithFilter(Map<String, String> params);
}
