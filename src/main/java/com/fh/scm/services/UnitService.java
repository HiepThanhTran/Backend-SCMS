package com.fh.scm.services;

import com.fh.scm.pojo.Unit;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface UnitService {

    Unit get(UUID id);

    void insert(Unit unit);

    void update(Unit unit);

    void delete(UUID id);

    void softDelete(UUID id);

    void insertOrUpdate(Unit unit);

    Long count();

    Boolean exists(UUID id);

    List<Unit> getAll(Map<String, String> params);
}
