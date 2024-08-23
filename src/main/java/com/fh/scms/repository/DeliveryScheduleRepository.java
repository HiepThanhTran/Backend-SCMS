package com.fh.scms.repository;

import com.fh.scms.pojo.DeliverySchedule;

import java.util.List;
import java.util.Map;

public interface DeliveryScheduleRepository {

    DeliverySchedule findById(Long id);

    void save(DeliverySchedule deliverySchedule);

    void update(DeliverySchedule deliverySchedule);

    void delete(Long id);

    Long count();

    List<DeliverySchedule> findAllWithFilter(Map<String, String> params);
}
