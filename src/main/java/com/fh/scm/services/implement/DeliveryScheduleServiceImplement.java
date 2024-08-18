package com.fh.scm.services.implement;

import com.fh.scm.pojo.DeliverySchedule;
import com.fh.scm.repository.DeliveryScheduleRepository;
import com.fh.scm.services.DeliveryScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DeliveryScheduleServiceImplement implements DeliveryScheduleService {

    @Autowired
    private DeliveryScheduleRepository deliveryScheduleRepository;

    @Override
    public DeliverySchedule get(Long id) {
        return this.deliveryScheduleRepository.get(id);
    }

    @Override
    public void insert(DeliverySchedule deliverySchedule) {
        this.deliveryScheduleRepository.insert(deliverySchedule);
    }

    @Override
    public void update(DeliverySchedule deliverySchedule) {
        this.deliveryScheduleRepository.update(deliverySchedule);
    }

    @Override
    public void delete(Long id) {
        this.deliveryScheduleRepository.delete(id);
    }

    @Override
    public void softDelete(Long id) {
        this.deliveryScheduleRepository.softDelete(id);
    }

    @Override
    public void insertOrUpdate(DeliverySchedule deliverySchedule) {
        this.deliveryScheduleRepository.insertOrUpdate(deliverySchedule);
    }

    @Override
    public Long count() {
        return this.deliveryScheduleRepository.count();
    }

    @Override
    public Boolean exists(Long id) {
        return this.deliveryScheduleRepository.exists(id);
    }

    @Override
    public List<DeliverySchedule> getAll(Map<String, String> params) {
        return this.deliveryScheduleRepository.getAll(params);
    }
}
