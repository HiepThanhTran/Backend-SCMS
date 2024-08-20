package com.fh.scm.services.implement;

import com.fh.scm.pojo.Unit;
import com.fh.scm.repository.UnitRepository;
import com.fh.scm.services.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UnitServiceImplement implements UnitService {

    @Autowired
    private UnitRepository unitRepository;

    @Override
    public Unit get(Long id) {
        return this.unitRepository.get(id);
    }

    @Override
    public void insert(Unit unit) {
        this.unitRepository.insert(unit);
    }

    @Override
    public void update(Unit unit) {
        this.unitRepository.update(unit);
    }

    @Override
    public void delete(Long id) {
        this.unitRepository.delete(id);
    }

    @Override
    public void softDelete(Long id) {
        this.unitRepository.softDelete(id);
    }

    @Override
    public void insertOrUpdate(Unit unit) {
        this.unitRepository.insertOrUpdate(unit);
    }

    @Override
    public Long count() {
        return this.unitRepository.count();
    }

    @Override
    public List<Unit> getAll(Map<String, String> params) {
        return this.unitRepository.getAll(params);
    }
}
