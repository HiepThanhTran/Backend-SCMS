package com.fh.scms.services.implement;

import com.fh.scms.pojo.Inventory;
import com.fh.scms.repository.InventoryRepository;
import com.fh.scms.services.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class InventoryServiceImplement implements InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Override
    public Inventory findById(Long id) {
        return this.inventoryRepository.findById(id);
    }

    @Override
    public void save(Inventory inventory) {
        this.inventoryRepository.save(inventory);
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
    public Long count() {
        return this.inventoryRepository.count();
    }

    @Override
    public List<Inventory> findAllWithFilter(Map<String, String> params) {
        return this.inventoryRepository.findAllWithFilter(params);
    }
}
