package com.fh.scm.repository;

import com.fh.scm.pojo.Warehouse;

import java.util.List;
import java.util.Map;

public interface WarehouseRepository {

    Warehouse get(Long id);

    void insert(Warehouse warehouse);

    void update(Warehouse warehouse);

    void delete(Long id);

    void softDelete(Long id);

    void insertOrUpdate(Warehouse warehouse);

    Long count();

    Boolean exists(Long id);

    List<Warehouse> getAll(Map<String, String> params);
}
