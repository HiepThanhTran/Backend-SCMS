package com.fh.scms.services.implement;

import com.fh.scms.pojo.Invoice;
import com.fh.scms.pojo.PaymentTerms;
import com.fh.scms.repository.PaymentTermsRepository;
import com.fh.scms.services.InvoiceService;
import com.fh.scms.services.PaymentTermsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class PaymentTermsServiceImplement implements PaymentTermsService {

    @Autowired
    private PaymentTermsRepository paymentTermsRepository;
    @Autowired
    private InvoiceService invoiceService;

    @Override
    public PaymentTerms findById(Long id) {
        return this.paymentTermsRepository.findById(id);
    }

    @Override
    public void save(PaymentTerms paymentTerms) {
        this.paymentTermsRepository.save(paymentTerms);
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
    public Long count() {
        return this.paymentTermsRepository.count();
    }

    @Override
    public List<PaymentTerms> findAllWithFilter(Map<String, String> params) {
        return this.paymentTermsRepository.findAllWithFilter(params);
    }
}
