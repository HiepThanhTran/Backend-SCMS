package com.fh.scm.services.implement;

import com.fh.scm.pojo.Shipment;
import com.fh.scm.repository.ShipmentRepository;
import com.fh.scm.services.ShipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;


@Service
@Transactional
public class ShipmentServiceImplement implements ShipmentService {

    @Autowired
    private ShipmentRepository shipmentRepository;

    @Override
    public Shipment get(UUID id) {
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
    public void delete(UUID id) {
        this.shipmentRepository.delete(id);
    }

    @Override
    public void softDelete(UUID id) {
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
    public Boolean exists(UUID id) {
        return this.shipmentRepository.exists(id);
    }

    @Override
    public List<Shipment> getAll(Map<String, String> params) {
        return this.shipmentRepository.getAll(params);
    }
}
