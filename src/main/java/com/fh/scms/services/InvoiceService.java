package com.fh.scms.services;

import com.fh.scms.dto.invoice.InvoiceResponse;
import com.fh.scms.pojo.Invoice;

import java.util.List;
import java.util.Map;

public interface InvoiceService {

    Invoice findById(Long id);

    void save(Invoice invoice);

    void update(Invoice invoice);

    void delete(Long id);

    Long count();

    List<Invoice> findAllWithFilter(Map<String, String> params);

    InvoiceResponse getInvoiceResponse(Invoice invoice);

    List<InvoiceResponse> getAllInvoiceResponse(Map<String, String> params);

    void payInvoice(Long invoiceId);
}
