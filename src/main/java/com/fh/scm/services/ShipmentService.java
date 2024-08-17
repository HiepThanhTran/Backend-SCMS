package com.fh.scm.services;

import com.fh.scm.pojo.Shipment;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface ShipmentService {

    Shipment get(UUID id);

    void insert(Shipment shipment);

    void update(Shipment shipment);

    void delete(UUID id);

    void softDelete(UUID id);

    void insertOrUpdate(Shipment shipment);

    Long count();

    Boolean exists(UUID id);

    List<Shipment> getAll(Map<String, String> params);
}
