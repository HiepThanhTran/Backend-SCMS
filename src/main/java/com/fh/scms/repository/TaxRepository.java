package com.fh.scms.repository;

import com.fh.scms.pojo.Tax;

import java.util.List;
import java.util.Map;

public interface TaxRepository {

    Tax get(Long id);

    void insert(Tax tax);

    void update(Tax tax);

    void delete(Long id);

    void softDelete(Long id);

    Long count();

    List<Tax> getAll(Map<String, String> params);
}
