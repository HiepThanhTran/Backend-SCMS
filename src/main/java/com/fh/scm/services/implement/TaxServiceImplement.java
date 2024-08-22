package com.fh.scm.services.implement;

import com.fh.scm.dto.tax.TaxResponse;
import com.fh.scm.pojo.Tax;
import com.fh.scm.repository.TaxRepository;
import com.fh.scm.services.TaxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class TaxServiceImplement implements TaxService {

    @Autowired
    private TaxRepository taxRepository;

    @Override
    public TaxResponse getTaxResponse(Tax tax) {
        return TaxResponse.builder()
                .id(tax.getId())
                .rate(tax.getRate())
                .region(tax.getRegion())
                .build();
    }

    @Override
    public List<TaxResponse> getAllTaxResponse(Map<String, String> params) {
        return this.taxRepository.getAll(params).stream()
                .map(this::getTaxResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Tax get(Long id) {
        return this.taxRepository.get(id);
    }

    @Override
    public void insert(Tax tax) {
        this.taxRepository.insert(tax);
    }

    @Override
    public void update(Tax tax) {
        this.taxRepository.update(tax);
    }

    @Override
    public void delete(Long id) {
        this.taxRepository.delete(id);
    }

    @Override
    public void softDelete(Long id) {
        this.taxRepository.softDelete(id);
    }

    @Override
    public Long count() {
        return this.taxRepository.count();
    }

    @Override
    public List<Tax> getAll(Map<String, String> params) {
        return this.taxRepository.getAll(params);
    }
}
