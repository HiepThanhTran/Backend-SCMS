package com.fh.scm.services.implement;

import com.fh.scm.pojo.Shipper;
import com.fh.scm.repository.ShipperRepository;
import com.fh.scm.services.ShipperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ShipperServiceImplement implements ShipperService {

    @Autowired
    private ShipperRepository shipperRepository;

    @Override
    public Shipper get(Long id) {
        return this.shipperRepository.get(id);
    }

    @Override
    public void insert(Shipper shipper) {
        this.shipperRepository.insert(shipper);
    }

    @Override
    public void update(Shipper shipper) {
        this.shipperRepository.update(shipper);
    }

    @Override
    public void delete(Long id) {
        this.shipperRepository.delete(id);
    }

    @Override
    public void softDelete(Long id) {
        this.shipperRepository.softDelete(id);
    }

    @Override
    public Long count() {
        return this.shipperRepository.count();
    }

    @Override
    public List<Shipper> getAll(Map<String, String> params) {
        return this.shipperRepository.getAll(params);
    }
}
