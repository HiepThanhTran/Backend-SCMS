package com.fh.scm.services.implement;

import com.fh.scm.pojo.Unit;
import com.fh.scm.services.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional
public class UnitServiceImplement implements UnitService {

    @Autowired
    private UnitService unitService;

    @Override
    public Unit get(UUID id) {
        return this.unitService.get(id);
    }

    @Override
    public void insert(Unit unit) {
        this.unitService.insert(unit);
    }

    @Override
    public void update(Unit unit) {
        this.unitService.update(unit);
    }

    @Override
    public void delete(UUID id) {
        this.unitService.delete(id);
    }

    @Override
    public void softDelete(UUID id) {
        this.unitService.softDelete(id);
    }

    @Override
    public void insertOrUpdate(Unit unit) {
        this.unitService.insertOrUpdate(unit);
    }

    @Override
    public Long count() {
        return this.unitService.count();
    }

    @Override
    public Boolean exists(UUID id) {
        return this.unitService.exists(id);
    }

    @Override
    public List<Unit> getAll(Map<String, String> params) {
        return this.unitService.getAll(params);
    }
}
