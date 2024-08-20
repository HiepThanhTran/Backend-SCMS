package com.fh.scm.services.implement;

import com.fh.scm.pojo.InventoryDetails;
import com.fh.scm.repository.InventoryDetailsRepository;
import com.fh.scm.services.InventoryDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class InventoryDetailsServiceImplement implements InventoryDetailsService {

    @Autowired
    private InventoryDetailsRepository inventoryDetailsRepository;

    @Override
    public InventoryDetails get(Long id) {
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
    public void delete(Long id) {
        this.inventoryDetailsRepository.delete(id);
    }

    @Override
    public Long count() {
        return this.inventoryDetailsRepository.count();
    }

    @Override
    public List<InventoryDetails> getAll(Map<String, String> params) {
        return this.inventoryDetailsRepository.getAll(params);
    }
}
