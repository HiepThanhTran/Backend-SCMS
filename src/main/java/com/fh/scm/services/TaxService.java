package com.fh.scm.services;

import com.fh.scm.pojo.Tax;

import java.util.List;
import java.util.Map;

public interface TaxService {

    Tax get(Long id);

    void insert(Tax tax);

    void update(Tax tax);

    void delete(Long id);

    void softDelete(Long id);

    Long count();

    List<Tax> getAll(Map<String, String> params);
}
