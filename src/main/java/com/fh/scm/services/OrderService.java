package com.fh.scm.services;

import com.fh.scm.dto.api.order.OrderDetailsReponse;
import com.fh.scm.dto.api.order.OrderRequest;
import com.fh.scm.dto.api.order.OrderResponse;
import com.fh.scm.pojo.Order;
import com.fh.scm.pojo.OrderDetails;
import com.fh.scm.pojo.User;

import java.util.List;
import java.util.Map;

public interface OrderService {

    OrderResponse getOrderResponse(Order order);

    OrderDetailsReponse getOrderDetailsReponse(OrderDetails orderDetails);

    List<OrderResponse> getAllOrderResponse(Map<String, String> params);

    void checkout(User user, OrderRequest orderRequest);

    void cancelOrder(User user, Long orderId);

    void updateOrderStatus(Long orderId, String status);

    Order get(Long id);

    void insert(Order order);

    void update(Order order);

    void delete(Long id);

    void softDelete(Long id);

    Long count();

    List<Order> getAll(Map<String, String> params);
}
