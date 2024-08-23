package com.fh.scms.repository;

import com.fh.scms.pojo.Warehouse;

import java.util.List;
import java.util.Map;

public interface WarehouseRepository {

    Warehouse get(Long id);

    void insert(Warehouse warehouse);

    void update(Warehouse warehouse);

    void delete(Long id);

    void softDelete(Long id);

    Long count();

    List<Warehouse> getAll(Map<String, String> params);
}
