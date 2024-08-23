package com.fh.scms.services.implement;

import com.fh.scms.pojo.DeliverySchedule;
import com.fh.scms.repository.DeliveryScheduleRepository;
import com.fh.scms.services.DeliveryScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
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
    public Long count() {
        return this.deliveryScheduleRepository.count();
    }

    @Override
    public List<DeliverySchedule> getAll(Map<String, String> params) {
        return this.deliveryScheduleRepository.getAll(params);
    }
}
