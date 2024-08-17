package com.fh.scm.services.implement;

import com.fh.scm.pojo.Supplier;
import com.fh.scm.repository.SupplierRepository;
import com.fh.scm.services.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional
public class SupplierServiceImplement implements SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

    @Override
    public Supplier get(UUID id) {
        return this.supplierRepository.get(id);
    }

    @Override
    public void insert(Supplier supplier) {
        this.supplierRepository.insert(supplier);
    }

    @Override
    public void update(Supplier supplier) {
        this.supplierRepository.update(supplier);
    }

    @Override
    public void delete(UUID id) {
        this.supplierRepository.delete(id);
    }

    @Override
    public void softDelete(UUID id) {
        this.supplierRepository.softDelete(id);
    }

    @Override
    public void insertOrUpdate(Supplier supplier) {
        this.supplierRepository.insertOrUpdate(supplier);
    }

    @Override
    public Long count() {
        return this.supplierRepository.count();
    }

    @Override
    public Boolean exists(UUID id) {
        return this.supplierRepository.exists(id);
    }

    @Override
    public List<Supplier> getAll(Map<String, String> params) {
        return this.supplierRepository.getAll(params);
    }
}
