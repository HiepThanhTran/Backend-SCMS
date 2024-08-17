package com.fh.scm.services;

import com.fh.scm.pojo.Supplier;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface SupplierService {

    Supplier get(UUID id);

    void insert(Supplier supplier);

    void update(Supplier supplier);

    void delete(UUID id);

    void softDelete(UUID id);

    void insertOrUpdate(Supplier supplier);

    Long count();

    Boolean exists(UUID id);

    List<Supplier> getAll(Map<String, String> params);
}
