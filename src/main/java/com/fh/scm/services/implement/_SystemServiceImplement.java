package com.fh.scm.services.implement;

import com.fh.scm.repository._SystemRepository;
import com.fh.scm.services._SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class _SystemServiceImplement implements _SystemService {

    @Autowired
    private _SystemRepository systemRepository;

    @Override
    public Boolean isExist(String name) {
        return this.systemRepository.isExist(name);
    }

    @Override
    public void insert(String name) {
        this.systemRepository.insert(name);
    }
}
