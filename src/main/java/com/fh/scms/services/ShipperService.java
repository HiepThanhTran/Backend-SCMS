package com.fh.scms.services;

import com.fh.scms.pojo.Shipper;

import java.util.List;
import java.util.Map;

public interface ShipperService {

    Shipper get(Long id);

    void insert(Shipper shipper);

    void update(Shipper shipper);

    void delete(Long id);

    void softDelete(Long id);

    Long count();

    List<Shipper> getAll(Map<String, String> params);
}
