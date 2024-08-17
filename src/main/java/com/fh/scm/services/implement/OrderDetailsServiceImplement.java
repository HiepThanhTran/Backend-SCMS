package com.fh.scm.services.implement;

import com.fh.scm.pojo.OrderDetails;
import com.fh.scm.repository.OrderDetailsRepository;
import com.fh.scm.services.OrderDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional
public class OrderDetailsServiceImplement implements OrderDetailsService {

    @Autowired
    private OrderDetailsRepository orderDetailsRepository;

    @Override
    public OrderDetails get(UUID id) {
        return this.orderDetailsRepository.get(id);
    }

    @Override
    public void insert(OrderDetails orderDetails) {
        this.orderDetailsRepository.insert(orderDetails);
    }

    @Override
    public void update(OrderDetails orderDetails) {
        this.orderDetailsRepository.update(orderDetails);
    }

    @Override
    public void delete(UUID id) {
        this.orderDetailsRepository.delete(id);
    }

    @Override
    public void softDelete(UUID id) {
        this.orderDetailsRepository.softDelete(id);
    }

    @Override
    public void insertOrUpdate(OrderDetails orderDetails) {
        this.orderDetailsRepository.insertOrUpdate(orderDetails);
    }

    @Override
    public Long count() {
        return this.orderDetailsRepository.count();
    }

    @Override
    public Boolean exists(UUID id) {
        return this.orderDetailsRepository.exists(id);
    }

    @Override
    public List<OrderDetails> getAll(Map<String, String> params) {
        return this.orderDetailsRepository.getAll(params);
    }
}
