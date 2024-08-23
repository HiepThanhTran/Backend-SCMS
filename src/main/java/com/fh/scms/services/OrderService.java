package com.fh.scms.services;

import com.fh.scms.dto.order.OrderDetailsReponse;
import com.fh.scms.dto.order.OrderRequest;
import com.fh.scms.dto.order.OrderResponse;
import com.fh.scms.pojo.Order;
import com.fh.scms.pojo.OrderDetails;
import com.fh.scms.pojo.User;

import java.util.List;
import java.util.Map;

public interface OrderService {

    OrderResponse getOrderResponse(Order order);

    OrderDetailsReponse getOrderDetailsReponse(OrderDetails orderDetails);

    List<OrderResponse> getAllOrderResponse(Map<String, String> params);

    void checkout(User user, OrderRequest orderRequest);

    void checkin(User user, OrderRequest orderRequest);

    void cancelOrder(User user, Long orderId);

    void updateOrderStatus(Long orderId, String status);

    Order findById(Long id);

    void save(Order order);

    void update(Order order);

    void delete(Long id);

    Long count();

    List<Order> findAllWithFilter(Map<String, String> params);
}
