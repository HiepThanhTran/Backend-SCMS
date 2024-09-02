package com.fh.scms.repository;

import com.fh.scms.pojo.Unit;

import java.util.List;
import java.util.Map;

public interface UnitRepository {

    Unit findById(Long id);

    void save(Unit unit);

    void update(Unit unit);

    void delete(Long id);

    Long count();

    List<Unit> findAllWithFilter(Map<String, String> params);
}
