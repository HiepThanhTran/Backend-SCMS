package com.fh.scm.services.implement;

import com.fh.scm.pojo.Tax;
import com.fh.scm.repository.TaxRepository;
import com.fh.scm.services.TaxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TaxServiceImplement implements TaxService {

    @Autowired
    private TaxRepository taxRepository;

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
