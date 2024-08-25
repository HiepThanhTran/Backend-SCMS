package com.fh.scms.repository;

import com.fh.scms.pojo.Inventory;

import java.util.List;
import java.util.Map;

public interface InventoryRepository {

    Inventory findById(Long id);

    void save(Inventory inventory);

    void update(Inventory inventory);

    void delete(Long id);

    Long count();

    List<Inventory> findAllWithFilter(Map<String, String> params);
}
