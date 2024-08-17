package com.fh.scm.services;

import com.fh.scm.pojo.Tax;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface TaxService {

    Tax get(UUID id);

    void insert(Tax tax);

    void update(Tax tax);

    void delete(UUID id);

    void softDelete(UUID id);

    void insertOrUpdate(Tax tax);

    Long count();

    Boolean exists(UUID id);

    List<Tax> getAll(Map<String, String> params);
}
