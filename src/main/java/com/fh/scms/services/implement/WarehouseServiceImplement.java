package com.fh.scms.services.implement;

import com.fh.scms.pojo.Warehouse;
import com.fh.scms.repository.WarehouseRepository;
import com.fh.scms.services.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
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
    public Long count() {
        return this.warehouseRepository.count();
    }

    @Override
    public List<Warehouse> getAll(Map<String, String> params) {
        return this.warehouseRepository.getAll(params);
    }
}
