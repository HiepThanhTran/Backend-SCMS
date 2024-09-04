package com.fh.scms.services.implement;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fh.scms.dto.order.*;
import com.fh.scms.enums.OrderStatus;
import com.fh.scms.enums.OrderType;
import com.fh.scms.enums.ShipmentStatus;
import com.fh.scms.pojo.*;
import com.fh.scms.repository.*;
import com.fh.scms.services.InventoryService;
import com.fh.scms.services.OrderService;
import com.fh.scms.services.ProductService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.lang.System;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderServiceImplement implements OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderDetailsRepository orderDetailsRepository;
    @Autowired
    private InventoryService inventoryService;
    @Autowired
    private ProductService productService;
    @Autowired
    private InvoiceRepository invoiceRepository;
    @Autowired
    private TaxRepository taxRepository;
    @Autowired
    private InventoryDetailsRepository inventoryDetailsRepository;

    @Override
    public Order findById(Long id) {
        return this.orderRepository.findById(id);
    }

    @Override
    public OrderResponseForTracking findByOrderNumber(String orderNumber) {
        return this.orderRepository.findByOrderNumber(orderNumber);
    }

    @Override
    public void save(Order order) {
        this.orderRepository.save(order);
    }

    @Override
    public void update(Order order) {
        this.orderRepository.update(order);
    }

    @Override
    public void delete(Long id) {
        this.orderRepository.delete(id);
    }

    @Override
    public Long count() {
        return this.orderRepository.count();
    }

    @Override
    public List<Order> findAllWithFilter(Map<String, String> params) {
        return this.orderRepository.findAllWithFilter(params);
    }

    @Override
    public List<Order> findAllBySupplierId(Long supplierId, Map<String, String> params) {
        return this.orderRepository.findAllBySupplierId(supplierId, params);
    }

    @Override
    public OrderResponse getOrderResponse(@NotNull Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .orderNumber(order.getOrderNumber())
                .invoiceNumber(order.getInvoice() != null ? order.getInvoice().getInvoiceNumber() : null)
                .type(order.getType())
                .status(order.getStatus())
                .orderDate(order.getCreatedAt())
                .expectedDelivery(order.getExpectedDelivery())
                .orderDetailsSet(order.getOrderDetailsSet()
                        .stream()
                        .map(this::getOrderDetailsReponse)
                        .collect(Collectors.toSet()))
                .build();
    }

    @Override
    public List<OrderResponse> getAllOrderResponse(@NotNull List<Order> orders) {
        return orders.stream().map(this::getOrderResponse).collect(Collectors.toList());
    }

    @Override
    public OrderDetailsReponse getOrderDetailsReponse(@NotNull OrderDetails orderDetails) {
        return OrderDetailsReponse.builder()
                .id(orderDetails.getId())
                .product(this.productService.getProductResponseForList(orderDetails.getProduct()))
                .quantity(orderDetails.getQuantity())
                .unitPrice(orderDetails.getUnitPrice())
                .build();
    }

    @Override
    public List<OrderDetails> getOrderDetailsById(Long orderId) {
        Order order = this.orderRepository.findById(orderId);

        return new ArrayList<>(order.getOrderDetailsSet());
    }

    @Override
    public void checkout(User user, @NotNull OrderRequest orderRequest) {
        if (orderRequest.getType() == OrderType.OUTBOUND) {
            // Nhóm các sản phẩm theo nhà cung cấp
            Map<Supplier, Set<OrderDetailsRequest>> groupedBySupplier = orderRequest.getOrderDetails().stream()
                    .collect(Collectors.groupingBy(odr -> this.productService.findById(odr.getProductId()).getSupplier(), Collectors.toSet()));

            // Tạo đơn hàng cho mỗi nhà cung cấp
            groupedBySupplier.forEach((supplier, orderDetailsList) -> {
                // Tạo đơn hàng mới
                Order order = Order.builder()
                        .user(user)
                        .type(orderRequest.getType())
                        .build();
                if (orderRequest.getStatus() != null) {
                    order.setStatus(orderRequest.getStatus());
                }
                this.orderRepository.save(order);

                // Tính toán tổng giá trị của đơn hàng
                final BigDecimal[] totalAmount = this.getTotalAmountOfOrder(orderDetailsList, order);

                // Tạo hóa đơn cho đơn hàng
                this.createInvoice(user, order, orderRequest, totalAmount);
            });
        }
    }

    private @NotNull InventoryDetails checkQuantityOfInventory(@NotNull Product orderProduct, Float orderQuantity) {
        Set<InventoryDetails> currentInventoryDetails = orderProduct.getInventoryDetailsSet();

        for (InventoryDetails details : currentInventoryDetails) {
            if (details.getQuantity() >= orderQuantity) {
                return details;
            }
        }

        throw new IllegalArgumentException("Không đủ hàng trong kho cho sản phẩm: " + orderProduct.getName());
    }

    private BigDecimal @NotNull [] getTotalAmountOfOrder(@NotNull Set<OrderDetailsRequest> orderDetailsRequests, Order order) {
        final BigDecimal[] totalAmount = {BigDecimal.ZERO};

        // Duyệt danh sách các sản phẩm trong đơn hàng
        orderDetailsRequests.forEach(odr -> {
            if (odr.getQuantity() > 0) {
                Product product = this.productService.findById(odr.getProductId());
                Optional.ofNullable(product).ifPresent(p -> {
                    // Tìm kiếm và kiểm tra tồn kho nào còn hàng không
                    InventoryDetails inventoryDetails = checkQuantityOfInventory(p, odr.getQuantity());

                    // Nếu có hàng trong kho và đủ số lượng khách yêu cầu thì cập nhật lại số lượng tồn kho
                    inventoryDetails.setQuantity(inventoryDetails.getQuantity() - odr.getQuantity());
                    this.inventoryDetailsRepository.update(inventoryDetails);

                    // Tạo chi tiết đơn hàng
                    this.createOrderDetails(order, product, inventoryDetails, odr);

                    totalAmount[0] = totalAmount[0].add(odr.getUnitPrice().multiply(BigDecimal.valueOf(odr.getQuantity())));
                });
            }
        });

        return totalAmount;
    }

    @Override
    public void checkin(User user, @NotNull OrderRequest orderRequest) {
        if (orderRequest.getType() == OrderType.INBOUND) {
            // Tìm kiếm tồn kho cần nhập hàng, nếu không tìm thấy thì báo lỗi
            Inventory inventory = this.inventoryService.findById(orderRequest.getInventoryId());
            Optional.ofNullable(inventory).orElseThrow(() -> new EntityNotFoundException("Không tìm thấy kho hàng"));

            // Nhóm các sản phẩm theo nhà cung cấp
            Map<Supplier, Set<OrderDetailsRequest>> groupedBySupplier = orderRequest.getOrderDetails().stream()
                    .collect(Collectors.groupingBy(odr -> this.productService.findById(odr.getProductId()).getSupplier(), Collectors.toSet()));

            // Tạo đơn hàng cho mỗi nhà cung cấp
            groupedBySupplier.forEach((supplier, orderDetailsList) -> {
                // Tạo đơn hàng mới
                Order order = Order.builder()
                        .user(user)
                        .type(orderRequest.getType())
                        .build();
                if (orderRequest.getStatus() != null) {
                    order.setStatus(orderRequest.getStatus());
                }
                this.orderRepository.save(order);

                this.checkCapacityOfWarehouse(inventory, orderRequest.getOrderDetails());

                // Nếu không vượt quá sức chứa thì tính toán tổng giá trị của đơn hàng
                final BigDecimal[] totalAmount = this.getTotalAmountOfOrder(orderRequest.getOrderDetails(), inventory, order);

                // Tạo hóa đơn cho đơn hàng
                this.createInvoice(user, order, orderRequest, totalAmount);
            });
        }
    }

    private void checkCapacityOfWarehouse(@NotNull Inventory inventory, @NotNull Set<OrderDetailsRequest> orderDetailsRequests) {
        // Tính tổng số lượng sản phẩm hiện tại của tất cả tồn kho trong kho hàng
        Float totalCurrentQuantity = this.inventoryDetailsRepository.getTotalQuantityByWarehouseId(inventory.getWarehouse().getId());

        // Tính tổng số lượng sản phẩm trong đơn hàng
        Float totalOrderQuantity = orderDetailsRequests.stream()
                .map(OrderDetailsRequest::getQuantity)
                .reduce(0F, Float::sum);

        // Tính tổng số lượng sản phẩm sau khi nhập hàng
        float totalQuantity = totalCurrentQuantity + totalOrderQuantity;

        // Kiểm tra tổng số lượng sản phẩm vượt quá sức chứa của kho hàng không
        if (totalQuantity > inventory.getWarehouse().getCapacity()) {
            throw new IllegalArgumentException("Số lượng sản phẩm vượt quá sức chứa của kho " + inventory.getWarehouse().getName());
        }
    }

    private BigDecimal @NotNull [] getTotalAmountOfOrder(@NotNull Set<OrderDetailsRequest> orderDetailsRequests, Inventory inventory, Order order) {
        final BigDecimal[] totalAmount = {BigDecimal.ZERO};

        // Duyệt danh sách các sản phẩm trong đơn hàng
        orderDetailsRequests.forEach(odr -> {
            if (odr.getQuantity() > 0) {
                Product product = this.productService.findById(odr.getProductId());

                Optional.ofNullable(product).ifPresent(p -> {
                    // Tìm tồn kho hiện tại của sản phẩm
                    InventoryDetails inventoryDetails = this.inventoryDetailsRepository
                            .findByInventoryIdAndProductId(inventory.getId(), p.getId());

                    // Chưa có tồn kho nào cho sản phẩm này thì tạo mới
                    if (inventoryDetails == null) {
                        inventoryDetails = InventoryDetails.builder()
                                .inventory(inventory)
                                .product(p)
                                .quantity(0F)
                                .build();
                        this.inventoryDetailsRepository.save(inventoryDetails);
                    }

                    // Sau khi tìm hoặc tạo mới tồn kho thì cập nhật lại số lượng tồn kho
                    inventoryDetails.setQuantity(inventoryDetails.getQuantity() + odr.getQuantity());
                    this.inventoryDetailsRepository.update(inventoryDetails);

                    // Tạo chi tiết đơn hàng
                    this.createOrderDetails(order, product, inventoryDetails, odr);

                    totalAmount[0] = totalAmount[0].add(odr.getUnitPrice().multiply(BigDecimal.valueOf(odr.getQuantity())));
                });
            }
        });

        return totalAmount;
    }

    @Override
    public void cancelOrder(User user, Long orderId) {
        Order order = this.orderRepository.findById(orderId);

        if (order == null || !Objects.equals(order.getUser().getId(), user.getId())) {
            throw new EntityNotFoundException("Không tìm thấy đơn hàng");
        }

        switch (order.getStatus()) {
            case CANCELLED:
                throw new IllegalStateException("Đơn hàng đã bị hủy");
            case CONFIRMED:
            case SHIPPED:
            case DELIVERED:
                throw new IllegalStateException("Đơn hàng không thể hủy vì đã được xác nhận");
            default:
                break;
        }

        order.setStatus(OrderStatus.CANCELLED);
        this.orderRepository.update(order);

        Set<OrderDetails> orderDetailsSet = order.getOrderDetailsSet();
        orderDetailsSet.forEach(od -> {
            InventoryDetails inventoryDetails = od.getInventoryDetails();
            inventoryDetails.setQuantity(inventoryDetails.getQuantity() + od.getQuantity());
            this.inventoryDetailsRepository.update(inventoryDetails);
        });

        Optional.ofNullable(this.invoiceRepository.findByOrderId(orderId)).ifPresent(invoice -> {
            order.setInvoice(null);
            this.invoiceRepository.delete(invoice.getId());
        });
    }

    @Override
    public void updateOrderStatus(Long orderId, @NotNull String status) {
        OrderStatus orderStatus;
        try {
            orderStatus = OrderStatus.valueOf(status.toUpperCase(Locale.getDefault()));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Trạng thái đơn hàng không hợp lệ");
        }

        Order order = this.orderRepository.findById(orderId);

        if (order == null) {
            throw new EntityNotFoundException("Không tìm thấy đơn hàng");
        }

        if (order.getStatus() == OrderStatus.CANCELLED) {
            throw new IllegalStateException("Đơn hàng đã bị hủy");
        }

        if (order.getStatus() == orderStatus) {
            throw new IllegalStateException("Đơn hàng đã ở trạng thái " + orderStatus);
        }

        Set<OrderDetails> orderDetailsSet = order.getOrderDetailsSet();
        switch (orderStatus) {
            case CANCELLED:
            case RETURNED:
                if (order.getType() == OrderType.OUTBOUND) {
                    orderDetailsSet.forEach(od -> {
                        InventoryDetails inventoryDetails = od.getInventoryDetails();
                        inventoryDetails.setQuantity(inventoryDetails.getQuantity() + od.getQuantity());
                        this.inventoryDetailsRepository.update(inventoryDetails);
                    });
                } else if (order.getType() == OrderType.INBOUND) {
                    orderDetailsSet.forEach(od -> {
                        InventoryDetails inventoryDetails = od.getInventoryDetails();
                        inventoryDetails.setQuantity(inventoryDetails.getQuantity() - od.getQuantity());
                        this.inventoryDetailsRepository.update(inventoryDetails);
                    });
                }
                break;
        }

        order.setStatus(orderStatus);
        this.orderRepository.update(order);
    }

    @Override
    public List<Order> findRecentlyOrders() {
        return this.orderRepository.findRecentOrders();
    }

    @Override
    public List<Order> findByDeliveryScheduleId(Long deliveryScheduleId) {
        return this.orderRepository.findByDeliveryScheduleId(deliveryScheduleId);
    }

    @Override
    public void save(@NotNull Order order, @NotNull List<Long> productIds, @NotNull List<Float> quantities, Long inventoryId) {
        Set<OrderDetailsRequest> orderDetailsRequests = productIds.stream()
                .map(this.productService::findById)
                .filter(Objects::nonNull)
                .map(product -> OrderDetailsRequest.builder()
                        .productId(product.getId())
                        .quantity(quantities.get(productIds.indexOf(product.getId())))
                        .unitPrice(product.getPrice())
                        .build())
                .collect(Collectors.toSet());

        OrderRequest orderRequest = OrderRequest.builder()
                .type(order.getType())
                .status(order.getStatus())
                .inventoryId(inventoryId)
                .orderDetails(orderDetailsRequests)
                .build();

        if (order.getType() == OrderType.OUTBOUND) {
            this.checkout(order.getUser(), orderRequest);
        } else if (order.getType() == OrderType.INBOUND) {
            this.checkin(order.getUser(), orderRequest);
        }
    }

    @Override
    public void update(@NotNull Order order, @NotNull List<Long> productIds, @NotNull List<Float> quantities, Long inventoryId) {
        List<OrderDetails> oldOrderDetailsSet = this.getOrderDetailsById(order.getId());
        Invoice oldInvoice = this.invoiceRepository.findByOrderId(order.getId());

        Map<Long, OrderDetails> idOldProductMap = oldOrderDetailsSet.stream()
                .collect(Collectors.toMap(od -> od.getProduct().getId(), od -> od));

        Set<OrderDetailsRequest> orderDetailsRequests = productIds.stream()
                .map(this.productService::findById)
                .filter(Objects::nonNull)
                .map(product -> OrderDetailsRequest.builder()
                        .productId(product.getId())
                        .quantity(quantities.get(productIds.indexOf(product.getId())))
                        .unitPrice(product.getPrice())
                        .build())
                .collect(Collectors.toSet());

        BigDecimal totalAmount = BigDecimal.ZERO;

        if (order.getType() == OrderType.OUTBOUND) {
            this.processOutboundOrder(order, orderDetailsRequests, idOldProductMap);
        } else if (order.getType() == OrderType.INBOUND) {
            this.processInboundOrder(order, inventoryId, orderDetailsRequests, idOldProductMap);
        }

        this.orderRepository.update(order);
        Tax tax = this.taxRepository.findByRegion("VN");
        BigDecimal finalAmount = totalAmount.add(totalAmount.multiply(tax.getRate()));
        oldInvoice.setTotalAmount(finalAmount);
        this.invoiceRepository.save(oldInvoice);
    }

    private void processOutboundOrder(Order order, @NotNull Set<OrderDetailsRequest> orderDetailsRequests, Map<Long, OrderDetails> idOldProductMap) {
        orderDetailsRequests.forEach(odr -> {
            Product product = this.productService.findById(odr.getProductId());
            if (product != null && odr.getQuantity() > 0) {
                InventoryDetails inventoryDetails = checkQuantityOfInventory(product, odr.getQuantity());
                OrderDetails orderDetails = idOldProductMap.get(product.getId());

                if (orderDetails != null) {
                    this.updateOrderDetails(orderDetails, product, odr.getQuantity(), product.getPrice(), inventoryDetails);
                } else {
                    this.createOrderDetails(order, product, inventoryDetails, odr);
                }

                inventoryDetails.setQuantity(inventoryDetails.getQuantity() - odr.getQuantity());
                this.inventoryDetailsRepository.update(inventoryDetails);
            }
        });
    }

    private void processInboundOrder(Order order, Long inventoryId, Set<OrderDetailsRequest> orderDetailsRequests, Map<Long, OrderDetails> idOldProductMap) {
        Inventory inventory = this.inventoryService.findById(inventoryId);
        this.checkCapacityOfWarehouse(inventory, orderDetailsRequests);

        orderDetailsRequests.forEach(odr -> {
            Product product = this.productService.findById(odr.getProductId());
            if (product != null && odr.getQuantity() > 0) {
                InventoryDetails inventoryDetails = this.inventoryDetailsRepository
                        .findByInventoryIdAndProductId(inventory.getId(), product.getId());

                if (inventoryDetails == null) {
                    inventoryDetails = InventoryDetails.builder()
                            .inventory(inventory)
                            .product(product)
                            .quantity(0F)
                            .build();
                    this.inventoryDetailsRepository.save(inventoryDetails);
                }

                OrderDetails orderDetails = idOldProductMap.get(product.getId());

                if (orderDetails != null) {
                    this.updateOrderDetails(orderDetails, product, odr.getQuantity(), product.getPrice(), inventoryDetails);
                } else {
                    this.createOrderDetails(order, product, inventoryDetails, odr);
                }

                inventoryDetails.setQuantity(inventoryDetails.getQuantity() + odr.getQuantity());
                this.inventoryDetailsRepository.update(inventoryDetails);
            }
        });
    }

    private void createOrderDetails(Order order, Product product, InventoryDetails inventoryDetails, @NotNull OrderDetailsRequest odr) {
        OrderDetails orderDetails = OrderDetails.builder()
                .order(order)
                .product(product)
                .inventoryDetails(inventoryDetails)
                .quantity(odr.getQuantity())
                .unitPrice(odr.getUnitPrice())
                .build();
        this.orderDetailsRepository.save(orderDetails);

    }

    private void updateOrderDetails(@NotNull OrderDetails orderDetails, Product product, Float quantity, BigDecimal unitPrice, InventoryDetails inventoryDetails) {
        orderDetails.setProduct(product);
        orderDetails.setQuantity(quantity);
        orderDetails.setUnitPrice(unitPrice);
        orderDetails.setInventoryDetails(inventoryDetails);
        this.orderDetailsRepository.update(orderDetails);
    }

    private void createInvoice(User user, Order order, @NotNull OrderRequest orderRequest, BigDecimal @NotNull [] totalAmount) {
        if (totalAmount[0].compareTo(BigDecimal.ZERO) < 1) {
            throw new IllegalArgumentException("Không có sản phẩm nào trong đơn hàng");
        }

        Tax tax = this.taxRepository.findByRegion("VN");
        Invoice invoice = Invoice.builder()
                .user(user)
                .order(order)
                .tax(tax)
                .totalAmount(totalAmount[0].add(totalAmount[0].multiply(tax.getRate())))
                .build();
        invoice.setCreatedAt(orderRequest.getCreatedAt());
        if (orderRequest.getPaid() != null) {
            invoice.setPaid(orderRequest.getPaid());
        }
        this.invoiceRepository.save(invoice);
    }
}
