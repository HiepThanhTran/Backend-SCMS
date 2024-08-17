package com.fh.scm.repository;

import com.fh.scm.pojo.Warehouse;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface WarehouseRepository {

    Warehouse get(UUID id);

    void insert(Warehouse warehouse);

    void update(Warehouse warehouse);

    void delete(UUID id);

    void softDelete(UUID id);

    void insertOrUpdate(Warehouse warehouse);

    Long count();

    Boolean exists(UUID id);

    List<Warehouse> getAll(Map<String, String> params);
}
