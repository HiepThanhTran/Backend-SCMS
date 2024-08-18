package com.fh.scm.services;

import com.fh.scm.pojo.Shipper;

import java.util.List;
import java.util.Map;

public interface ShipperService {

    Shipper get(Long id);

    void insert(Shipper shipper);

    void update(Shipper shipper);

    void delete(Long id);

    void softDelete(Long id);

    void insertOrUpdate(Shipper shipper);

    Long count();

    Boolean exists(Long id);

    List<Shipper> getAll(Map<String, String> params);
}
