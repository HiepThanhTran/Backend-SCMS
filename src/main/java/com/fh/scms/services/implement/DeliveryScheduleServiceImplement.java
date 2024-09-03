package com.fh.scms.services.implement;

import com.fh.scms.pojo.DeliverySchedule;
import com.fh.scms.pojo.Order;
import com.fh.scms.repository.DeliveryScheduleRepository;
import com.fh.scms.repository.OrderRepository;
import com.fh.scms.services.DeliveryScheduleService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class DeliveryScheduleServiceImplement implements DeliveryScheduleService {

    @Autowired
    private DeliveryScheduleRepository deliveryScheduleRepository;
    @Autowired
    private OrderRepository orderRepository;

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
        DeliverySchedule deliverySchedule = this.deliveryScheduleRepository.findById(id);
        List<Order> orders = new ArrayList<>(deliverySchedule.getOrderSet());
        orders.forEach(order -> {
            order.setDeliverySchedule(null);
            this.orderRepository.update(order);
        });

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

    @Override
    public void save(DeliverySchedule deliverySchedule, @NotNull List<String> orderIds) {
        processSchedule(deliverySchedule, orderIds);

        this.deliveryScheduleRepository.save(deliverySchedule);
    }

    @Override
    public void update(DeliverySchedule deliverySchedule, @NotNull List<String> orderIds) {
        processSchedule(deliverySchedule, orderIds);

        this.deliveryScheduleRepository.update(deliverySchedule);
    }

    private void processSchedule(DeliverySchedule deliverySchedule, @NotNull List<String> orderIds) {
        Set<Order> orders = new HashSet<>();
        for (String orderId : orderIds) {
            Order order = this.orderRepository.findById(Long.parseLong(orderId));
            if (order != null) {
                orders.add(order);
                order.setDeliverySchedule(deliverySchedule);
            }
        }

        deliverySchedule.setOrderSet(orders);
    }
}
