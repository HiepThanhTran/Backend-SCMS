package com.fh.scm.services;

import com.fh.scm.pojo.Order;

import java.util.List;
import java.util.Map;

public interface OrderService {

    Order get(Long id);

    void insert(Order order);

    void update(Order order);

    void delete(Long id);

    void softDelete(Long id);

    void insertOrUpdate(Order order);

    Long count();

    List<Order> getAll(Map<String, String> params);
}
