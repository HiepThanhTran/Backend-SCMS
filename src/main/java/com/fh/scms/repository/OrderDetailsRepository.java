package com.fh.scms.repository;

import com.fh.scms.pojo.OrderDetails;

import java.util.List;
import java.util.Map;

public interface OrderDetailsRepository {

    OrderDetails findById(Long id);

    void save(OrderDetails orderDetails);

    void update(OrderDetails orderDetails);

    void delete(Long id);

    Long count();

    List<OrderDetails> findAllWithFilter(Map<String, String> params);
}
