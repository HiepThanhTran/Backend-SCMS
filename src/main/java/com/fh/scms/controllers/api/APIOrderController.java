package com.fh.scms.controllers.api;

import com.fh.scms.dto.MessageResponse;
import com.fh.scms.dto.order.OrderRequest;
import com.fh.scms.dto.order.OrderResponse;
import com.fh.scms.pojo.User;
import com.fh.scms.services.CartService;
import com.fh.scms.services.OrderService;
import com.fh.scms.services.UserService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/orders", produces = "application/json; charset=UTF-8")
public class APIOrderController {

    private final CartService cartService;
    private final OrderService orderService;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<?> listOrders(Principal principal, @RequestParam(required = false, defaultValue = "") Map<String, String> params) {
        User user = this.userService.findByUsername(principal.getName());
        Optional.ofNullable(user).orElseThrow(() -> new EntityNotFoundException("không tìm thấy người dùng"));

        params.put("user", user.getId().toString());
        List<OrderResponse> orderList = this.orderService.getAllOrderResponse(params);

        return ResponseEntity.ok(orderList);
    }

    @PostMapping(path = "/checkout")
    public ResponseEntity<?> checkout(Principal principal, @RequestBody @Valid OrderRequest orderRequest) {
        User user = this.userService.findByUsername(principal.getName());
        Optional.ofNullable(user).orElseThrow(() -> new EntityNotFoundException("không tìm thấy người dùng"));

        this.orderService.checkout(user, orderRequest);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping(path = "/checkin")
    public ResponseEntity<?> checkin(Principal principal, @RequestBody @Valid OrderRequest orderRequest) {
        User user = this.userService.findByUsername(principal.getName());
        Optional.ofNullable(user).orElseThrow(() -> new EntityNotFoundException("không tìm thấy người dùng"));

        this.orderService.checkin(user, orderRequest);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping(path = "/{orderId}/cancel")
    public ResponseEntity<?> cancelOrder(Principal principal, @PathVariable(value = "orderId") Long orderId) {
        User user = this.userService.findByUsername(principal.getName());
        Optional.ofNullable(user).orElseThrow(() -> new EntityNotFoundException("không tìm thấy người dùng"));

        this.orderService.cancelOrder(user, orderId);

        return ResponseEntity.ok().build();
    }

    @PatchMapping(path = "/{orderId}/status")
    public ResponseEntity<?> updateOrderStatus(@PathVariable Long orderId, @RequestBody Map<String, String> params) {
        this.orderService.updateOrderStatus(orderId, params.get("status"));

        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleEntityNotFoundException(@NotNull HttpServletRequest req, EntityNotFoundException e) {
        LoggerFactory.getLogger(req.getRequestURI()).error(e.getMessage(), e);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(List.of(new MessageResponse(e.getMessage())));
    }

    @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class})
    public ResponseEntity<?> handleIllegalArgumentException(@NotNull HttpServletRequest req, IllegalArgumentException e) {
        LoggerFactory.getLogger(req.getRequestURI()).error(e.getMessage(), e);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(List.of(new MessageResponse(e.getMessage())));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDenied(@NotNull HttpServletRequest req, AccessDeniedException e) {
        LoggerFactory.getLogger(req.getRequestURI()).error(e.getMessage(), e);

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(List.of(new MessageResponse(e.getMessage())));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(@NotNull HttpServletRequest req, Exception e) {
        LoggerFactory.getLogger(req.getRequestURI()).error(e.getMessage(), e);

        return ResponseEntity.badRequest().body(List.of(new MessageResponse(e.getMessage())));
    }
}
