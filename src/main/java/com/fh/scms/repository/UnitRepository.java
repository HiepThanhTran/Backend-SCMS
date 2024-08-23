package com.fh.scms.repository;

import com.fh.scms.pojo.Unit;

import java.util.List;
import java.util.Map;

public interface UnitRepository {

    Unit get(Long id);

    List<Unit> getByProduct(Long productId);

    void insert(Unit unit);

    void update(Unit unit);

    void delete(Long id);

    void softDelete(Long id);

    Long count();

    List<Unit> getAll(Map<String, String> params);
}
