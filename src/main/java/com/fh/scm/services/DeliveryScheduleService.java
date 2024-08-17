package com.fh.scm.services;

import com.fh.scm.pojo.DeliverySchedule;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface DeliveryScheduleService {

    DeliverySchedule get(UUID id);

    void insert(DeliverySchedule deliverySchedule);

    void update(DeliverySchedule deliverySchedule);

    void delete(UUID id);

    void softDelete(UUID id);

    void insertOrUpdate(DeliverySchedule deliverySchedule);

    Long count();

    Boolean exists(UUID id);

    List<DeliverySchedule> getAll(Map<String, String> params);
}
