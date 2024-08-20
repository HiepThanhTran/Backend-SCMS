package com.fh.scm.repository;

import com.fh.scm.pojo.InventoryDetails;

import java.util.List;
import java.util.Map;

public interface InventoryDetailsRepository {

    InventoryDetails get(Long id);

    void insert(InventoryDetails inventoryDetails);

    void update(InventoryDetails inventoryDetails);

    void delete(Long id);

    void insertOrUpdate(InventoryDetails inventoryDetails);

    Long count();

    List<InventoryDetails> getAll(Map<String, String> params);
}
