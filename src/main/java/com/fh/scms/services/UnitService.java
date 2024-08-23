package com.fh.scms.services;

import com.fh.scms.dto.unit.UnitResponse;
import com.fh.scms.pojo.Unit;

import java.util.List;
import java.util.Map;

public interface UnitService {

    UnitResponse getUnitResponse(Unit unit);

    List<UnitResponse> getAllUnitResponse(Map<String, String> params);

    Unit get(Long id);

    List<Unit> getByProduct(Long productId);

    void insert(Unit unit);

    void update(Unit unit);

    void delete(Long id);

    void softDelete(Long id);

    Long count();

    List<Unit> getAll(Map<String, String> params);
}
