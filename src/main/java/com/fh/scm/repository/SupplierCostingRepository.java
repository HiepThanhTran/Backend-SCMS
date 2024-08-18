package com.fh.scm.repository;

import com.fh.scm.pojo.SupplierCosting;

import java.util.List;
import java.util.Map;

public interface SupplierCostingRepository {

    SupplierCosting get(Long id);

    void insert(SupplierCosting supplierCosting);

    void update(SupplierCosting supplierCosting);

    void delete(Long id);

    void softDelete(Long id);

    void insertOrUpdate(SupplierCosting supplierCosting);

    Long count();

    Boolean exists(Long id);

    List<SupplierCosting> getAll(Map<String, String> params);
}
