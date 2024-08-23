package com.fh.scms.services.implement;

import com.fh.scms.pojo.Shipper;
import com.fh.scms.repository.ShipperRepository;
import com.fh.scms.services.ShipperService;
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
    public Shipper findById(Long id) {
        return this.shipperRepository.findById(id);
    }

    @Override
    public void save(Shipper shipper) {
        this.shipperRepository.save(shipper);
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
    public Long count() {
        return this.shipperRepository.count();
    }

    @Override
    public List<Shipper> findAllWithFilter(Map<String, String> params) {
        return this.shipperRepository.findAllWithFilter(params);
    }
}
