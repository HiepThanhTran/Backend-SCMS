package com.fh.scms.services;

import com.fh.scms.dto.order.OrderDetailsReponse;
import com.fh.scms.dto.order.OrderRequest;
import com.fh.scms.dto.order.OrderResponse;
import com.fh.scms.dto.order.OrderResponseForTracking;
import com.fh.scms.pojo.Order;
import com.fh.scms.pojo.OrderDetails;
import com.fh.scms.pojo.User;

import java.util.List;
import java.util.Map;

public interface OrderService {

    Order findById(Long id);

    OrderResponseForTracking findByOrderNumber(String orderNumber);

    void save(Order order);

    void update(Order order);

    void delete(Long id);

    Long count();

    List<Order> findAllWithFilter(Map<String, String> params);

    List<Order> findAllBySupplierId(Long supplierId, Map<String, String> params);

    OrderResponse getOrderResponse(Order order);

    List<OrderResponse> getAllOrderResponse(List<Order> orders);

    OrderDetailsReponse getOrderDetailsReponse(OrderDetails orderDetails);

    List<OrderDetails> getOrderDetailsById(Long orderId);

    void checkout(User user, OrderRequest orderRequest);

    void checkin(User user, OrderRequest orderRequest);

    void cancelOrder(User user, Long orderId);

    void updateOrderStatus(Long orderId, String status);

    List<Order> findRecentlyOrders();

    List<Order> findByDeliveryScheduleId(Long deliveryScheduleId);

    void save(Order order, List<Long> productIds, List<Float> quantities, Long inventoryId);

    void update(Order order, List<Long> productIds, List<Float> quantities, Long inventoryId);
}
