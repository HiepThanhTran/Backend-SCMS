package com.fh.scms.services.implement;

import com.fh.scms.dto.unit.UnitResponse;
import com.fh.scms.pojo.Unit;
import com.fh.scms.repository.UnitRepository;
import com.fh.scms.services.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class UnitServiceImplement implements UnitService {

    @Autowired
    private UnitRepository unitRepository;

    @Override
    public UnitResponse getUnitResponse(Unit unit) {
        return UnitResponse.builder()
                .id(unit.getId())
                .name(unit.getName())
                .abbreviation(unit.getAbbreviation())
                .build();
    }

    @Override
    public List<UnitResponse> getAllUnitResponse(Map<String, String> params) {
        return this.unitRepository.getAll(params).stream()
                .map(this::getUnitResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Unit get(Long id) {
        return this.unitRepository.get(id);
    }

    @Override
    public List<Unit> getByProduct(Long productId) {
        return this.unitRepository.getByProduct(productId);
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
    public Long count() {
        return this.unitRepository.count();
    }

    @Override
    public List<Unit> getAll(Map<String, String> params) {
        return this.unitRepository.getAll(params);
    }
}
