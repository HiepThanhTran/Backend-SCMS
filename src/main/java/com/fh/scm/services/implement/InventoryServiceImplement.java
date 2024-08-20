package com.fh.scm.services.implement;

import com.fh.scm.pojo.Inventory;
import com.fh.scm.repository.InventoryRepository;
import com.fh.scm.services.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class InventoryServiceImplement implements InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Override
    public Inventory get(Long id) {
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
    public void delete(Long id) {
        this.inventoryRepository.delete(id);
    }

    @Override
    public void softDelete(Long id) {
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
    public List<Inventory> getAll(Map<String, String> params) {
        return this.inventoryRepository.getAll(params);
    }
}
