package com.fh.scms.services.implement;

import com.fh.scms.dto.order.OrderDetailsReponse;
import com.fh.scms.dto.order.OrderDetailsRequest;
import com.fh.scms.dto.order.OrderRequest;
import com.fh.scms.dto.order.OrderResponse;
import com.fh.scms.enums.OrderStatus;
import com.fh.scms.enums.OrderType;
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
import java.math.BigDecimal;
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
    private InventoryDetailsRepository inventoryDetailsRepository;
    @Autowired
    private WarehouseRepository warehouseRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private InvoiceRepository invoiceRepository;
    @Autowired
    private TaxRepository taxRepository;

    private static @NotNull InventoryDetails checkAndGetInventoryDetails(OrderDetailsRequest odr, @NotNull Product product) {
        InventoryDetails inventoryDetails = null;

        Set<InventoryDetails> inventoryDetailsSet = product.getInventoryDetailsSet();
        for (InventoryDetails details : inventoryDetailsSet) {
            if (details.getQuantity() >= odr.getQuantity()) {
                inventoryDetails = details;
                break;
            }
        }

        // Nếu không có tồn kho nào đủ số lượng sản phẩm thì thông báo lỗi
        if (inventoryDetails == null) {
            throw new IllegalArgumentException(String.format("Số lượng %s không đủ trong kho", product.getName()));
        }

        return inventoryDetails;
    }

    @Override
    public OrderResponse getOrderResponse(@NotNull Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .orderNumber(order.getOrderNumber())
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
    public OrderDetailsReponse getOrderDetailsReponse(@NotNull OrderDetails orderDetails) {
        return OrderDetailsReponse.builder()
                .id(orderDetails.getId())
                .product(this.productService.getProductResponse(orderDetails.getProduct()))
                .quantity(orderDetails.getQuantity())
                .unitPrice(orderDetails.getUnitPrice())
                .build();
    }

    @Override
    public List<OrderResponse> getAllOrderResponse(Map<String, String> params) {
        return this.orderRepository.findAllWithFilter(params).stream()
                .map(this::getOrderResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void checkout(User user, @NotNull OrderRequest orderRequest) {
        if (orderRequest.getType() == OrderType.OUTBOUND) {
            Order order = Order.builder()
                    .user(user)
                    .type(orderRequest.getType())
                    .build();
            this.orderRepository.save(order);

            final BigDecimal[] totalAmount = this.checkAndGetTotalAmountOfOrder(orderRequest, order);

            Tax tax = this.taxRepository.findByRegion("VN");
            Invoice invoice = Invoice.builder()
                    .user(user)
                    .order(order)
                    .tax(tax)
                    .totalAmount(this.calculateTotalAmountWithTax(totalAmount[0], tax))
                    .build();
            this.invoiceRepository.save(invoice);
        }
    }

    @Override
    public void checkin(User user, @NotNull OrderRequest orderRequest) {
        if (orderRequest.getType() == OrderType.INBOUND) {
            Inventory inventory = this.inventoryService.findById(orderRequest.getInventoryId());

            if (inventory == null) {
                throw new EntityNotFoundException("Không tìm thấy kho hàng");
            }

            Order order = Order.builder()
                    .user(user)
                    .type(orderRequest.getType())
                    .build();
            this.orderRepository.save(order);

            // Tính tổng số lượng sản phẩm hiện tại của tất cả tồn kho trong kho hàng
            Float totalCurrentQuantity = this.inventoryDetailsRepository.getTotalQuantityByWarehouseId(inventory.getWarehouse().getId());

            // Tính tổng số lượng sản phẩm trong đơn hàng
            Float totalOrderQuantity = orderRequest.getOrderDetails().stream()
                    .map(OrderDetailsRequest::getQuantity)
                    .reduce(0F, Float::sum);

            // Tính tổng số lượng sản phẩm sau khi nhập hàng
            float totalQuantity = totalCurrentQuantity + totalOrderQuantity;

            // Kiểm tra tổng số lượng sản phẩm vượt quá sức chứa của kho hàng không
            if (totalQuantity > inventory.getWarehouse().getCapacity()) {
                throw new IllegalArgumentException("Số lượng sản phẩm vượt quá sức chứa của kho " + inventory.getWarehouse().getName());
            }

            final BigDecimal[] totalAmount = this.updateAmountOfInventory(orderRequest, inventory, order);

            Tax tax = this.taxRepository.findByRegion("VN");
            Invoice invoice = Invoice.builder()
                    .user(user)
                    .order(order)
                    .tax(tax)
                    .totalAmount(this.calculateTotalAmountWithTax(totalAmount[0], tax))
                    .build();
            this.invoiceRepository.save(invoice);
        }
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

        order.setCancel(true);
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
            case DELIVERED:
//                if (order.getType() == OrderType.INBOUND) {
//                    orderDetailsSet.forEach(od -> {
//                        Inventory inventory = od.getProduct().getInventory();
//                        inventory.setQuantity(inventory.getQuantity() + od.getQuantity());
//                        this.inventoryService.update(inventory);
//                    });
//                }
                break;
            case CANCELLED:
            case RETURNED:
                orderDetailsSet.forEach(od -> {
                    InventoryDetails inventoryDetails = od.getInventoryDetails();
                    inventoryDetails.setQuantity(inventoryDetails.getQuantity() + od.getQuantity());
                    this.inventoryDetailsRepository.update(inventoryDetails);
                });

                Optional.ofNullable(this.invoiceRepository.findByOrderId(orderId)).ifPresent(invoice -> {
                    order.setInvoice(null);
                    this.invoiceRepository.delete(invoice.getId());
                });
                break;
        }

        order.setStatus(orderStatus);
        this.orderRepository.update(order);
    }

    @Override
    public Order findById(Long id) {
        return this.orderRepository.findById(id);
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

    private @NotNull BigDecimal calculateTotalAmountWithTax(@NotNull BigDecimal totalAmount, @NotNull Tax tax) {
        return totalAmount.add(totalAmount.multiply(tax.getRate()));
    }

    private void createOrderDetails(Order order, BigDecimal @NotNull [] totalAmount, @NotNull OrderDetailsRequest odr, Product product, InventoryDetails inventoryDetails) {
        OrderDetails orderDetails = OrderDetails.builder()
                .order(order)
                .product(product)
                .quantity(odr.getQuantity())
                .unitPrice(odr.getUnitPrice())
                .inventoryDetails(inventoryDetails)
                .build();
        this.orderDetailsRepository.save(orderDetails);

        totalAmount[0] = totalAmount[0].add(odr.getUnitPrice().multiply(BigDecimal.valueOf(odr.getQuantity())));
    }

    private BigDecimal @NotNull [] checkAndGetTotalAmountOfOrder(@NotNull OrderRequest orderRequest, Order order) {
        Set<OrderDetailsRequest> orderDetailsRequests = orderRequest.getOrderDetails();
        final BigDecimal[] totalAmount = {BigDecimal.ZERO};

        orderDetailsRequests.forEach(odr -> {
            if (odr.getQuantity() > 0) {
                Product product = this.productService.findById(odr.getProductId());

                // Tìm kiếm và kiểm tra tồn kho nào còn hàng không
                InventoryDetails inventoryDetails = checkAndGetInventoryDetails(odr, product);

                // Cập nhật lại số lượng tồn kho
                inventoryDetails.setQuantity(inventoryDetails.getQuantity() - odr.getQuantity());
                this.inventoryDetailsRepository.update(inventoryDetails);

                this.createOrderDetails(order, totalAmount, odr, product, inventoryDetails);
            }
        });

        return totalAmount;
    }

    private BigDecimal @NotNull [] updateAmountOfInventory(@NotNull OrderRequest orderRequest, Inventory inventory, Order order) {
        Set<OrderDetailsRequest> orderDetailsRequests = orderRequest.getOrderDetails();
        final BigDecimal[] totalAmount = {BigDecimal.ZERO};

        orderDetailsRequests.forEach(odr -> {
            if (odr.getQuantity() > 0) {
                Product product = this.productService.findById(odr.getProductId());
                InventoryDetails inventoryDetails = this.inventoryDetailsRepository
                        .findByInventoryIdAndProductId(inventory.getId(), product.getId());

                // Chưa có tồn kho nào cho sản phẩm này thì tạo mới
                if (inventoryDetails == null) {
                    inventoryDetails = InventoryDetails.builder()
                            .inventory(inventory)
                            .product(product)
                            .quantity(0F)
                            .build();
                    this.inventoryDetailsRepository.save(inventoryDetails);
                }

                // Có rồi thì cập nhật lại số lượng tồn kho
                inventoryDetails.setQuantity(inventoryDetails.getQuantity() + odr.getQuantity());
                this.inventoryDetailsRepository.update(inventoryDetails);

                this.createOrderDetails(order, totalAmount, odr, product, inventoryDetails);
            }
        });

        return totalAmount;
    }
}
