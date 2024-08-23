package com.fh.scms.repository;

import com.fh.scms.pojo.Invoice;

import java.util.List;
import java.util.Map;

public interface InvoiceRepository {

    Invoice findById(Long id);

    Invoice findByOrderId(Long orderId);

    void save(Invoice invoice);

    void update(Invoice invoice);

    void delete(Long id);

    Long count();

    List<Invoice> findAllWithFilter(Map<String, String> params);
}
