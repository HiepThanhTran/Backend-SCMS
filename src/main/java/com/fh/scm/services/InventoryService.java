package com.fh.scm.services;

import com.fh.scm.pojo.Inventory;

import java.util.List;
import java.util.Map;

public interface InventoryService {

    Inventory get(Long id);

    void insert(Inventory inventory);

    void update(Inventory inventory);

    void delete(Long id);

    void softDelete(Long id);

    void insertOrUpdate(Inventory inventory);

    Long count();

    List<Inventory> getAll(Map<String, String> params);
}
