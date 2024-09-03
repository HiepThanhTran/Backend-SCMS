package com.fh.scms.repository;

import com.fh.scms.pojo.Order;

import java.util.List;
import java.util.Map;

public interface OrderRepository {

    List<Order> findRecentOrders();

    List<Order> findByDeliveryScheduleId(Long deliveryScheduleId);

    Order findById(Long id);

    void save(Order order);

    void update(Order order);

    void delete(Long id);

    Long count();

    List<Order> findAllWithFilter(Map<String, String> params);

    List<Order> findAllBySupplierId(Long supplierId, Map<String, String> params);
}
