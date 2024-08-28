package com.fh.scms.services;

import com.fh.scms.pojo.DeliverySchedule;

import java.util.List;
import java.util.Map;

public interface DeliveryScheduleService {

    DeliverySchedule findById(Long id);

    void save(DeliverySchedule deliverySchedule);

    void update(DeliverySchedule deliverySchedule);

    void delete(Long id);

    Long count();

    List<DeliverySchedule> findAllWithFilter(Map<String, String> params);

    void save(DeliverySchedule deliverySchedule, List<String> orderIds);

    void update(DeliverySchedule deliverySchedule, List<String> orderIds);
}
