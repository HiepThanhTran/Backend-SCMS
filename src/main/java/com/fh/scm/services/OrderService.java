package com.fh.scm.services;

import com.fh.scm.pojo.Order;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface OrderService {

    Order get(UUID id);

    void insert(Order order);

    void update(Order order);

    void delete(UUID id);

    void softDelete(UUID id);

    void insertOrUpdate(Order order);

    Long count();

    Boolean exists(UUID id);

    List<Order> getAll(Map<String, String> params);
}
