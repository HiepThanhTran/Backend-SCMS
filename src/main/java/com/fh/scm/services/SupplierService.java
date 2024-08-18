package com.fh.scm.services;

import com.fh.scm.pojo.Supplier;

import java.util.List;
import java.util.Map;

public interface SupplierService {

    Supplier get(Long id);

    Supplier getByPhone(String phone);

    void insert(Supplier supplier);

    void update(Supplier supplier);

    void delete(Long id);

    void softDelete(Long id);

    void insertOrUpdate(Supplier supplier);

    Long count();

    Boolean exists(Long id);

    List<Supplier> getAll(Map<String, String> params);
}
