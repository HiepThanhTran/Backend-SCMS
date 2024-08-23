package com.fh.scms.repository;

import com.fh.scms.pojo.SupplierCosting;

import java.util.List;
import java.util.Map;

public interface SupplierCostingRepository {

    SupplierCosting findById(Long id);

    void save(SupplierCosting supplierCosting);

    void update(SupplierCosting supplierCosting);

    void delete(Long id);

    Long count();

    List<SupplierCosting> findAllWithFilter(Map<String, String> params);
}
