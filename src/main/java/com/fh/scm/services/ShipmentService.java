package com.fh.scm.services;

import com.fh.scm.pojo.Shipment;

import java.util.List;
import java.util.Map;

public interface ShipmentService {

    Shipment get(Long id);

    void insert(Shipment shipment);

    void update(Shipment shipment);

    void delete(Long id);

    void softDelete(Long id);

    void insertOrUpdate(Shipment shipment);

    Long count();

    Boolean exists(Long id);

    List<Shipment> getAll(Map<String, String> params);
}
