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
    public DeliverySchedule findById(Long id) {
        return this.deliveryScheduleRepository.findById(id);
    }

    @Override
    public void save(DeliverySchedule deliverySchedule) {
        this.deliveryScheduleRepository.save(deliverySchedule);
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
    public Long count() {
        return this.deliveryScheduleRepository.count();
    }

    @Override
    public List<DeliverySchedule> findAllWithFilter(Map<String, String> params) {
        return this.deliveryScheduleRepository.findAllWithFilter(params);
    }
}
