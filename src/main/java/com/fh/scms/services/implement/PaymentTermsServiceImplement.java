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
        PaymentTerms paymentTerms = this.paymentTermsRepository.get(id);
        List<Invoice> invoicesToUpdate = new ArrayList<>(paymentTerms.getInvoiceSet());

        invoicesToUpdate.forEach(invoice -> {
            invoice.setPaymentTerms(null);
            this.invoiceService.update(invoice);
        });

        this.paymentTermsRepository.delete(id);
    }

    @Override
    public void softDelete(Long id) {
        this.paymentTermsRepository.softDelete(id);
    }

    @Override
    public Long count() {
        return this.paymentTermsRepository.count();
    }

    @Override
    public List<PaymentTerms> getAll(Map<String, String> params) {
        return this.paymentTermsRepository.getAll(params);
    }
}
