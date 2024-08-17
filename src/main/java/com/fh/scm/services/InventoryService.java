package com.fh.scm.services;

import com.fh.scm.pojo.Inventory;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface InventoryService {

    Inventory get(UUID id);

    void insert(Inventory inventory);

    void update(Inventory inventory);

    void delete(UUID id);

    void softDelete(UUID id);

    void insertOrUpdate(Inventory inventory);

    Long count();

    Boolean exists(UUID id);

    List<Inventory> getAll(Map<String, String> params);
}
