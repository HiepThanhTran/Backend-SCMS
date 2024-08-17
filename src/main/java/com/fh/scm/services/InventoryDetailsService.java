package com.fh.scm.services;

import com.fh.scm.pojo.InventoryDetails;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface InventoryDetailsService {

    InventoryDetails get(UUID id);

    void insert(InventoryDetails inventoryDetails);

    void update(InventoryDetails inventoryDetails);

    void delete(UUID id);

    void softDelete(UUID id);

    void insertOrUpdate(InventoryDetails inventoryDetails);

    Long count();

    Boolean exists(UUID id);

    List<InventoryDetails> getAll(Map<String, String> params);
}
