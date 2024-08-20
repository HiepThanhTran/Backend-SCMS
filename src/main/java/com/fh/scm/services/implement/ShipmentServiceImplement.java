package com.fh.scm.services.implement;

import com.fh.scm.pojo.Shipment;
import com.fh.scm.repository.ShipmentRepository;
import com.fh.scm.services.ShipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class ShipmentServiceImplement implements ShipmentService {

    @Autowired
    private ShipmentRepository shipmentRepository;

    @Override
    public Shipment get(Long id) {
        return this.shipmentRepository.get(id);
    }

    @Override
    public void insert(Shipment shipment) {
        this.shipmentRepository.insert(shipment);
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
    public void softDelete(Long id) {
        this.shipmentRepository.softDelete(id);
    }

    @Override
    public void insertOrUpdate(Shipment shipment) {
        this.shipmentRepository.insertOrUpdate(shipment);
    }

    @Override
    public Long count() {
        return this.shipmentRepository.count();
    }

    @Override
    public List<Shipment> getAll(Map<String, String> params) {
        return this.shipmentRepository.getAll(params);
    }
}
