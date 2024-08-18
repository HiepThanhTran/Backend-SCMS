package com.fh.scm.services.implement;

import com.fh.scm.pojo.Unit;
import com.fh.scm.services.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UnitServiceImplement implements UnitService {

    @Autowired
    private UnitService unitService;

    @Override
    public Unit get(Long id) {
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
    public void delete(Long id) {
        this.unitService.delete(id);
    }

    @Override
    public void softDelete(Long id) {
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
    public Boolean exists(Long id) {
        return this.unitService.exists(id);
    }

    @Override
    public List<Unit> getAll(Map<String, String> params) {
        return this.unitService.getAll(params);
    }
}
