package com.fh.scm.services;

import com.fh.scm.pojo.OrderDetails;

import java.util.List;
import java.util.Map;

public interface OrderDetailsService {

    OrderDetails get(Long id);

    void insert(OrderDetails orderDetails);

    void update(OrderDetails orderDetails);

    void delete(Long id);

    void softDelete(Long id);

    void insertOrUpdate(OrderDetails orderDetails);

    Long count();

    Boolean exists(Long id);

    List<OrderDetails> getAll(Map<String, String> params);
}
