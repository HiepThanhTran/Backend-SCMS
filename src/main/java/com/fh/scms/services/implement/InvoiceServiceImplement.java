package com.fh.scms.services.implement;

import com.fh.scms.dto.invoice.InvoiceResponse;
import com.fh.scms.pojo.Invoice;
import com.fh.scms.pojo.Tax;
import com.fh.scms.repository.InvoiceRepository;
import com.fh.scms.services.InvoiceService;
import com.fh.scms.services.TaxService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class InvoiceServiceImplement implements InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;
    @Autowired
    private TaxService taxService;

    @Override
    public Invoice findById(Long id) {
        return this.invoiceRepository.findById(id);
    }

    @Override
    public Invoice findByInvoiceNumber(String invoiceNumber) {
        return this.invoiceRepository.findByInvoiceNumber(invoiceNumber);
    }

    @Override
    public void save(Invoice invoice) {
        this.invoiceRepository.save(invoice);
    }

    @Override
    public void update(@NotNull Invoice invoice) {
        if (invoice.getTax() != null) {
            invoice.setTotalAmount(invoice.getTotalAmount().add(invoice.getTotalAmount().multiply(invoice.getTax().getRate())));
        }

        this.invoiceRepository.update(invoice);
    }

    @Override
    public void delete(Long id) {
        this.invoiceRepository.delete(id);
    }

    @Override
    public Long count() {
        return this.invoiceRepository.count();
    }

    @Override
    public List<Invoice> findAllWithFilter(Map<String, String> params) {
        return this.invoiceRepository.findAllWithFilter(params);
    }

    @Override
    public InvoiceResponse getInvoiceResponse(@NotNull Invoice invoice) {
        return InvoiceResponse.builder()
                .id(invoice.getId())
                .invoiceNumber(invoice.getInvoiceNumber())
                .isPaid(invoice.getPaid())
                .totalAmount(invoice.getTotalAmount())
                .invoiceDate(invoice.getCreatedAt())
                .tax(this.taxService.getTaxResponse(invoice.getTax()))
                .build();
    }

    @Override
    public List<InvoiceResponse> getAllInvoiceResponse(Map<String, String> params) {
        return this.invoiceRepository.findAllWithFilter(params).stream()
                .map(this::getInvoiceResponse)
                .collect(Collectors.toList());
    }
}
