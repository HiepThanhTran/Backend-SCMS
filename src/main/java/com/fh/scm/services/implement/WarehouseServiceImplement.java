package com.fh.scm.services.implement;

import com.fh.scm.pojo.Warehouse;
import com.fh.scm.repository.WarehouseRepository;
import com.fh.scm.services.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class WarehouseServiceImplement implements WarehouseService {

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Override
    public Warehouse get(Long id) {
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
    public void delete(Long id) {
        this.warehouseRepository.delete(id);
    }

    @Override
    public void softDelete(Long id) {
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
    public List<Warehouse> getAll(Map<String, String> params) {
        return this.warehouseRepository.getAll(params);
    }
}
