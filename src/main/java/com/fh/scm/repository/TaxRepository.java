package com.fh.scm.repository;

import com.fh.scm.pojo.Tax;

import java.util.List;
import java.util.Map;

public interface TaxRepository {

    Tax get(Long id);

    void insert(Tax tax);

    void update(Tax tax);

    void delete(Long id);

    void softDelete(Long id);

    void insertOrUpdate(Tax tax);

    Long count();

    Boolean exists(Long id);

    List<Tax> getAll(Map<String, String> params);
}
