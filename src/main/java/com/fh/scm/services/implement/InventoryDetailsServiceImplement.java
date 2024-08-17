package com.fh.scm.services.implement;

import com.fh.scm.pojo.InventoryDetails;
import com.fh.scm.repository.InventoryDetailsRepository;
import com.fh.scm.services.InventoryDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional
public class InventoryDetailsServiceImplement implements InventoryDetailsService {

    @Autowired
    private InventoryDetailsRepository inventoryDetailsRepository;

    @Override
    public InventoryDetails get(UUID id) {
        return this.inventoryDetailsRepository.get(id);
    }

    @Override
    public void insert(InventoryDetails inventoryDetails) {
        this.inventoryDetailsRepository.insert(inventoryDetails);
    }

    @Override
    public void update(InventoryDetails inventoryDetails) {
        this.inventoryDetailsRepository.update(inventoryDetails);
    }

    @Override
    public void delete(UUID id) {
        this.inventoryDetailsRepository.delete(id);
    }

    @Override
    public void softDelete(UUID id) {
        this.inventoryDetailsRepository.softDelete(id);
    }

    @Override
    public void insertOrUpdate(InventoryDetails inventoryDetails) {
        this.inventoryDetailsRepository.insertOrUpdate(inventoryDetails);
    }

    @Override
    public Long count() {
        return this.inventoryDetailsRepository.count();
    }

    @Override
    public Boolean exists(UUID id) {
        return this.inventoryDetailsRepository.exists(id);
    }

    @Override
    public List<InventoryDetails> getAll(Map<String, String> params) {
        return this.inventoryDetailsRepository.getAll(params);
    }
}
