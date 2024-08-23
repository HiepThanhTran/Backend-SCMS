package com.fh.scms.services;

import com.fh.scms.pojo.Warehouse;

import java.util.List;
import java.util.Map;

public interface WarehouseService {

    Warehouse findById(Long id);

    void save(Warehouse warehouse);

    void update(Warehouse warehouse);

    void delete(Long id);

    Long count();

    List<Warehouse> findAllWithFilter(Map<String, String> params);
}
