package com.fh.scm.services;

import com.fh.scm.pojo.Invoice;

import java.util.List;
import java.util.Map;

public interface InvoiceService {

    Invoice get(Long id);

    void insert(Invoice invoice);

    void update(Invoice invoice);

    void delete(Long id);

    void softDelete(Long id);

    Long count();

    List<Invoice> getAll(Map<String, String> params);
}
