package com.fh.scm.services;

import com.fh.scm.pojo.Invoice;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface InvoiceService {

    Invoice get(UUID id);

    void insert(Invoice invoice);

    void update(Invoice invoice);

    void delete(UUID id);

    void softDelete(UUID id);

    void insertOrUpdate(Invoice invoice);

    Long count();

    Boolean exists(UUID id);

    List<Invoice> getAll(Map<String, String> params);
}
