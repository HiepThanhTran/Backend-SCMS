package com.fh.scm.services.implement;

import com.fh.scm.pojo.Inventory;
import com.fh.scm.repository.InventoryRepository;
import com.fh.scm.services.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional
public class InventoryServiceImplement implements InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Override
    public Inventory get(UUID id) {
        return this.inventoryRepository.get(id);
    }

    @Override
    public void insert(Inventory inventory) {
        this.inventoryRepository.insert(inventory);
    }

    @Override
    public void update(Inventory inventory) {
        this.inventoryRepository.update(inventory);
    }

    @Override
    public void delete(UUID id) {
        this.inventoryRepository.delete(id);
    }

    @Override
    public void softDelete(UUID id) {
        this.inventoryRepository.softDelete(id);
    }

    @Override
    public void insertOrUpdate(Inventory inventory) {
        this.inventoryRepository.insertOrUpdate(inventory);
    }

    @Override
    public Long count() {
        return this.inventoryRepository.count();
    }

    @Override
    public Boolean exists(UUID id) {
        return this.inventoryRepository.exists(id);
    }

    @Override
    public List<Inventory> getAll(Map<String, String> params) {
        return this.inventoryRepository.getAll(params);
    }
}
