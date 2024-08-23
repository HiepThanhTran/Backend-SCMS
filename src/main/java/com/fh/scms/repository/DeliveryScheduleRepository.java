package com.fh.scms.repository;

import com.fh.scms.pojo.DeliverySchedule;

import java.util.List;
import java.util.Map;

public interface DeliveryScheduleRepository {

    DeliverySchedule get(Long id);

    void insert(DeliverySchedule deliverySchedule);

    void update(DeliverySchedule deliverySchedule);

    void delete(Long id);

    void softDelete(Long id);

    Long count();

    List<DeliverySchedule> getAll(Map<String, String> params);
}
