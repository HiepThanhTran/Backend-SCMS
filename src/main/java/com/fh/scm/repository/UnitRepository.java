package com.fh.scm.repository;

import com.fh.scm.pojo.Unit;

import java.util.List;
import java.util.Map;

public interface UnitRepository {

    Unit get(Long id);

    void insert(Unit unit);

    void update(Unit unit);

    void delete(Long id);

    void softDelete(Long id);

    void insertOrUpdate(Unit unit);

    Long count();

    List<Unit> getAll(Map<String, String> params);
}
