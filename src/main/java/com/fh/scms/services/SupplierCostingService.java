package com.fh.scms.services;

import com.fh.scms.pojo.SupplierCosting;

import java.util.List;
import java.util.Map;

public interface SupplierCostingService {

    SupplierCosting get(Long id);

    void insert(SupplierCosting supplierCosting);

    void update(SupplierCosting supplierCosting);

    void delete(Long id);

    Long count();

    List<SupplierCosting> getAll(Map<String, String> params);
}
