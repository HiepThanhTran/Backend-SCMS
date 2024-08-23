package com.fh.scms.services;

import com.fh.scms.dto.tax.TaxResponse;
import com.fh.scms.pojo.Tax;

import java.util.List;
import java.util.Map;

public interface TaxService {

    TaxResponse getTaxResponse(Tax tax);

    List<TaxResponse> getAllTaxResponse(Map<String, String> params);

    Tax findById(Long id);

    void save(Tax tax);

    void update(Tax tax);

    void delete(Long id);

    Long count();

    List<Tax> findAllWithFilter(Map<String, String> params);
}
