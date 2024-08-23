package com.fh.scms.services.implement;

import com.fh.scms.repository._SystemRepository;
import com.fh.scms.services._SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
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
