package com.fh.scm.services;

import com.fh.scm.pojo.Shipper;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface ShipperService {

    Shipper get(UUID id);

    void insert(Shipper shipper);

    void update(Shipper shipper);

    void delete(UUID id);

    void softDelete(UUID id);

    void insertOrUpdate(Shipper shipper);

    Long count();

    Boolean exists(UUID id);

    List<Shipper> getAll(Map<String, String> params);
}
