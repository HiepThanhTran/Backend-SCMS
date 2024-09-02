package com.fh.scms.services.implement;

import com.fh.scms.dto.unit.UnitResponse;
import com.fh.scms.pojo.Product;
import com.fh.scms.pojo.Unit;
import com.fh.scms.repository.ProductRepository;
import com.fh.scms.repository.UnitRepository;
import com.fh.scms.services.UnitService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class UnitServiceImplement implements UnitService {

    @Autowired
    private UnitRepository unitRepository;

    @Override
    public Unit findById(Long id) {
        return this.unitRepository.findById(id);
    }

    @Override
    public void save(Unit unit) {
        this.unitRepository.save(unit);
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
    public Long count() {
        return this.unitRepository.count();
    }

    @Override
    public List<Unit> findAllWithFilter(Map<String, String> params) {
        return this.unitRepository.findAllWithFilter(params);
    }

    @Override
    public UnitResponse getUnitResponse(@NotNull Unit unit) {
        return UnitResponse.builder()
                .id(unit.getId())
                .name(unit.getName())
                .abbreviation(unit.getAbbreviation())
                .build();
    }

    @Override
    public List<UnitResponse> getAllUnitResponse(Map<String, String> params) {
        return this.unitRepository.findAllWithFilter(params).stream()
                .map(this::getUnitResponse)
                .collect(Collectors.toList());
    }
}
