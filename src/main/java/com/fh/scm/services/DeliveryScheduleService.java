package com.fh.scm.services;

import com.fh.scm.pojo.DeliverySchedule;

import java.util.List;
import java.util.Map;

public interface DeliveryScheduleService {

    DeliverySchedule get(Long id);

    void insert(DeliverySchedule deliverySchedule);

    void update(DeliverySchedule deliverySchedule);

    void delete(Long id);

    void softDelete(Long id);

    Long count();

    List<DeliverySchedule> getAll(Map<String, String> params);
}
