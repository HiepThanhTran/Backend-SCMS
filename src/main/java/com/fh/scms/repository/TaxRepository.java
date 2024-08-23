package com.fh.scms.repository;

import com.fh.scms.pojo.Tax;

import java.util.List;
import java.util.Map;

public interface TaxRepository {

    Tax findById(Long id);

    Tax findByRegion(String region);

    void save(Tax tax);

    void update(Tax tax);

    void delete(Long id);

    Long count();

    List<Tax> findAllWithFilter(Map<String, String> params);
}
