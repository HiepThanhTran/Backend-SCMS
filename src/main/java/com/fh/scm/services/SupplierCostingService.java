package com.fh.scm.services;

import com.fh.scm.pojo.SupplierCosting;

import java.util.List;
import java.util.Map;

public interface SupplierCostingService {

    SupplierCosting get(Long id);

    void insert(SupplierCosting supplierCosting);

    void update(SupplierCosting supplierCosting);

    void delete(Long id);

    void insertOrUpdate(SupplierCosting supplierCosting);

    Long count();

    List<SupplierCosting> getAll(Map<String, String> params);
}
