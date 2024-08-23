package com.fh.scms.repository;

import com.fh.scms.pojo.Order;

import java.util.List;
import java.util.Map;

public interface OrderRepository {

    Order get(Long id);

    void insert(Order order);

    void update(Order order);

    void delete(Long id);

    void softDelete(Long id);

    Long count();

    List<Order> getAll(Map<String, String> params);
}
