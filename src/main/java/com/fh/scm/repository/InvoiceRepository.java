package com.fh.scm.repository;

import com.fh.scm.pojo.Invoice;

import java.util.List;
import java.util.Map;

public interface InvoiceRepository {

    Invoice get(Long id);

    void insert(Invoice invoice);

    void update(Invoice invoice);

    void delete(Long id);

    void softDelete(Long id);

    void insertOrUpdate(Invoice invoice);

    Long count();

    Boolean exists(Long id);

    List<Invoice> getAll(Map<String, String> params);
}
