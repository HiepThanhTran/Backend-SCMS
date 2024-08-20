package com.fh.scm.repository;

import com.fh.scm.pojo.OrderDetails;

import java.util.List;
import java.util.Map;

public interface OrderDetailsRepository {

    OrderDetails get(Long id);

    void insert(OrderDetails orderDetails);

    void update(OrderDetails orderDetails);

    void delete(Long id);

    Long count();

    List<OrderDetails> getAll(Map<String, String> params);
}
