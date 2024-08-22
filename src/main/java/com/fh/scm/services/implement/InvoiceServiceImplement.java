package com.fh.scm.services.implement;

import com.fh.scm.pojo.Invoice;
import com.fh.scm.repository.InvoiceRepository;
import com.fh.scm.services.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class InvoiceServiceImplement implements InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Override
    public Invoice get(Long id) {
        return this.invoiceRepository.get(id);
    }

    @Override
    public void insert(Invoice invoice) {
        this.invoiceRepository.insert(invoice);
    }

    @Override
    public void update(Invoice invoice) {
        this.invoiceRepository.update(invoice);
    }

    @Override
    public void delete(Long id) {
        this.invoiceRepository.delete(id);
    }

    @Override
    public void softDelete(Long id) {
        this.invoiceRepository.softDelete(id);
    }

    @Override
    public Long count() {
        return this.invoiceRepository.count();
    }

    @Override
    public List<Invoice> getAll(Map<String, String> params) {
        return this.invoiceRepository.getAll(params);
    }
}
