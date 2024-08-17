package com.fh.scm.services;

import com.fh.scm.pojo.SupplierCosting;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface SupplierCostingService {

    SupplierCosting get(UUID id);

    void insert(SupplierCosting supplierCosting);

    void update(SupplierCosting supplierCosting);

    void delete(UUID id);

    void softDelete(UUID id);

    void insertOrUpdate(SupplierCosting supplierCosting);

    Long count();

    Boolean exists(UUID id);

    List<SupplierCosting> getAll(Map<String, String> params);
}
