package com.fh.scms.services;

import com.fh.scms.pojo.SupplierCosting;

import java.util.List;
import java.util.Map;

public interface SupplierCostingService {

    SupplierCosting findById(Long id);

    void save(SupplierCosting supplierCosting);

    void update(SupplierCosting supplierCosting);

    void delete(Long id);

    Long count();

    List<SupplierCosting> findAllWithFilter(Map<String, String> params);
}
