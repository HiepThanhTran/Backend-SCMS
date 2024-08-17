package com.fh.scm.services.implement;

import com.fh.scm.pojo.Warehouse;
import com.fh.scm.repository.WarehouseRepository;
import com.fh.scm.services.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional
public class WarehouseServiceImplement implements WarehouseService {

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Override
    public Warehouse get(UUID id) {
        return this.warehouseRepository.get(id);
    }

    @Override
    public void insert(Warehouse warehouse) {
        this.warehouseRepository.insert(warehouse);
    }

    @Override
    public void update(Warehouse warehouse) {
        this.warehouseRepository.update(warehouse);
    }

    @Override
    public void delete(UUID id) {
        this.warehouseRepository.delete(id);
    }

    @Override
    public void softDelete(UUID id) {
        this.warehouseRepository.softDelete(id);
    }

    @Override
    public void insertOrUpdate(Warehouse warehouse) {
        this.warehouseRepository.insertOrUpdate(warehouse);
    }

    @Override
    public Long count() {
        return this.warehouseRepository.count();
    }

    @Override
    public Boolean exists(UUID id) {
        return this.warehouseRepository.exists(id);
    }

    @Override
    public List<Warehouse> getAll(Map<String, String> params) {
        return this.warehouseRepository.getAll(params);
    }
}
