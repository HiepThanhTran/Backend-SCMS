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

    void insertOrUpdate(DeliverySchedule deliverySchedule);

    Long count();

    Boolean exists(Long id);

    List<DeliverySchedule> getAll(Map<String, String> params);
}
