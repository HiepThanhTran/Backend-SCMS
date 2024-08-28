package com.fh.scms.services;

import com.fh.scms.dto.unit.UnitResponse;
import com.fh.scms.pojo.Unit;

import java.util.List;
import java.util.Map;

public interface UnitService {

    Unit findById(Long id);

    void save(Unit unit);

    void update(Unit unit);

    void delete(Long id);

    Long count();

    List<Unit> findAllWithFilter(Map<String, String> params);

    UnitResponse getUnitResponse(Unit unit);

    List<UnitResponse> getAllUnitResponse(Map<String, String> params);
}
