package com.fh.scm.repository;

import com.fh.scm.pojo.Shipment;

import java.util.List;
import java.util.Map;

public interface ShipmentRepository {

    Shipment get(Long id);

    void insert(Shipment shipment);

    void update(Shipment shipment);

    void delete(Long id);

    void softDelete(Long id);

    Long count();

    List<Shipment> getAll(Map<String, String> params);
}
