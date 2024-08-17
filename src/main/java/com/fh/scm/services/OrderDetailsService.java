package com.fh.scm.services;

import com.fh.scm.pojo.OrderDetails;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface OrderDetailsService {

    OrderDetails get(UUID id);

    void insert(OrderDetails orderDetails);

    void update(OrderDetails orderDetails);

    void delete(UUID id);

    void softDelete(UUID id);

    void insertOrUpdate(OrderDetails orderDetails);

    Long count();

    Boolean exists(UUID id);

    List<OrderDetails> getAll(Map<String, String> params);
}
