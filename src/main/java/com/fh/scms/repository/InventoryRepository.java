package com.fh.scms.repository;

import com.fh.scms.pojo.Inventory;

import java.util.List;
import java.util.Map;

public interface InventoryRepository {

    Inventory get(Long id);

    void insert(Inventory inventory);

    void update(Inventory inventory);

    void delete(Long id);

    void softDelete(Long id);

    Long count();

    List<Inventory> getAll(Map<String, String> params);
}
