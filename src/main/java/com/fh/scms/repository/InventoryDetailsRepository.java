package com.fh.scms.repository;

import com.fh.scms.pojo.InventoryDetails;

import java.util.List;
import java.util.Map;

public interface InventoryDetailsRepository {

    Float getTotalQuantityByWarehouseId(Long warehouseId);

    InventoryDetails findById(Long id);

    InventoryDetails findByInventoryIdAndProductId(Long inventoryId, Long productId);

    void save(InventoryDetails inventoryDetails);

    void update(InventoryDetails inventoryDetails);

    void delete(Long id);

    Long count();

    List<InventoryDetails> findAllWithFilter(Map<String, String> params);
}
