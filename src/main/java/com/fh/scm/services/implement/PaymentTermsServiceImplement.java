package com.fh.scm.services.implement;

import com.fh.scm.pojo.PaymentTerms;
import com.fh.scm.repository.PaymentTermsRepository;
import com.fh.scm.services.PaymentTermsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PaymentTermsServiceImplement implements PaymentTermsService {

    @Autowired
    private PaymentTermsRepository paymentTermsRepository;

    @Override
    public PaymentTerms get(Long id) {
        return this.paymentTermsRepository.get(id);
    }

    @Override
    public void insert(PaymentTerms paymentTerms) {
        this.paymentTermsRepository.insert(paymentTerms);
    }

    @Override
    public void update(PaymentTerms paymentTerms) {
        this.paymentTermsRepository.update(paymentTerms);
    }

    @Override
    public void delete(Long id) {
        this.paymentTermsRepository.delete(id);
    }

    @Override
    public void softDelete(Long id) {
        this.paymentTermsRepository.softDelete(id);
    }

    @Override
    public void insertOrUpdate(PaymentTerms paymentTerms) {
        this.paymentTermsRepository.insertOrUpdate(paymentTerms);
    }

    @Override
    public Long count() {
        return this.paymentTermsRepository.count();
    }

    @Override
    public Boolean exists(Long id) {
        return this.paymentTermsRepository.exists(id);
    }

    @Override
    public List<PaymentTerms> getAll(Map<String, String> params) {
        return this.paymentTermsRepository.getAll(params);
    }
}
