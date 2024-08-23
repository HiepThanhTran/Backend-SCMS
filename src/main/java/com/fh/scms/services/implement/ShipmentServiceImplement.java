package com.fh.scms.services.implement;

import com.fh.scms.pojo.Shipment;
import com.fh.scms.repository.ShipmentRepository;
import com.fh.scms.services.ShipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Service
@Transactional
public class ShipmentServiceImplement implements ShipmentService {

    @Autowired
    private ShipmentRepository shipmentRepository;

    @Override
    public Shipment findById(Long id) {
        return this.shipmentRepository.findById(id);
    }

    @Override
    public void save(Shipment shipment) {
        this.shipmentRepository.save(shipment);
    }

    @Override
    public void update(Shipment shipment) {
        this.shipmentRepository.update(shipment);
    }

    @Override
    public void delete(Long id) {
        this.shipmentRepository.delete(id);
    }

    @Override
    public Long count() {
        return this.shipmentRepository.count();
    }

    @Override
    public List<Shipment> findAllWithFilter(Map<String, String> params) {
        return this.shipmentRepository.findAllWithFilter(params);
    }
}
