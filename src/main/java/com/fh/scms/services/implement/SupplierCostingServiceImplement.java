package com.fh.scms.services.implement;

import com.fh.scms.pojo.SupplierCosting;
import com.fh.scms.repository.SupplierCostingRepository;
import com.fh.scms.services.SupplierCostingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class SupplierCostingServiceImplement implements SupplierCostingService {

    @Autowired
    private SupplierCostingRepository supplierCostingRepository;

    @Override
    public SupplierCosting findById(Long id) {
        return this.supplierCostingRepository.findById(id);
    }

    @Override
    public void save(SupplierCosting supplierCosting) {
        this.supplierCostingRepository.save(supplierCosting);
    }

    @Override
    public void update(SupplierCosting supplierCosting) {
        this.supplierCostingRepository.update(supplierCosting);
    }

    @Override
    public void delete(Long id) {
        this.supplierCostingRepository.delete(id);
    }

    @Override
    public Long count() {
        return this.supplierCostingRepository.count();
    }

    @Override
    public List<SupplierCosting> findAllWithFilter(Map<String, String> params) {
        return this.supplierCostingRepository.findAllWithFilter(params);
    }
}
