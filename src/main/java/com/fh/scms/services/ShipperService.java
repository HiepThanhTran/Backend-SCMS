package com.fh.scms.services;

import com.fh.scms.dto.shipper.ShipperDTO;
import com.fh.scms.pojo.Shipper;

import java.util.List;
import java.util.Map;

public interface ShipperService {

    Shipper findById(Long id);

    void save(Shipper shipper);

    void update(Shipper shipper);

    void delete(Long id);

    Long count();

    List<Shipper> findAllWithFilter(Map<String, String> params);

    ShipperDTO getShipperResponse(Shipper shipper);

    Shipper getProfileShipper(String username);

    ShipperDTO updateProfileShipper(String username, ShipperDTO shipperDTO);
}
