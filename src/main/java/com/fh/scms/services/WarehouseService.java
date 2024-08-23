package com.fh.scms.services;

import com.fh.scms.pojo.Warehouse;

import java.util.List;
import java.util.Map;

public interface WarehouseService {

    Warehouse get(Long id);

    void insert(Warehouse warehouse);

    void update(Warehouse warehouse);

    void delete(Long id);

    void softDelete(Long id);

    Long count();

    List<Warehouse> getAll(Map<String, String> params);
}
