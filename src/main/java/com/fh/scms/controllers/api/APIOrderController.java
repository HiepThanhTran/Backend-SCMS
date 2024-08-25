package com.fh.scms.controllers.api;

import com.fh.scms.dto.MessageResponse;
import com.fh.scms.dto.order.OrderRequest;
import com.fh.scms.dto.order.OrderResponse;
import com.fh.scms.pojo.User;
import com.fh.scms.services.CartService;
import com.fh.scms.services.OrderService;
import com.fh.scms.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
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
        if (Optional.ofNullable(user).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        params.put("userId", user.getId().toString());
        List<OrderResponse> orderList = this.orderService.getAllOrderResponse(params);

        return ResponseEntity.ok(orderList);
    }

    @PostMapping(path = "/checkout")
    public ResponseEntity<?> checkout(Principal principal, @RequestBody @Valid OrderRequest orderRequest) {
        User user = this.userService.findByUsername(principal.getName());
        if (Optional.ofNullable(user).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        try {
            this.orderService.checkout(user, orderRequest);

            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (IllegalStateException | IllegalArgumentException | EntityNotFoundException e) {
            if (e instanceof EntityNotFoundException) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @PostMapping(path = "/checkin")
    public ResponseEntity<?> checkin(Principal principal, @RequestBody @Valid OrderRequest orderRequest) {
        User user = this.userService.findByUsername(principal.getName());
        if (Optional.ofNullable(user).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        try {
            this.orderService.checkin(user, orderRequest);

            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (IllegalStateException | IllegalArgumentException | EntityNotFoundException e) {
            if (e instanceof EntityNotFoundException) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @PatchMapping(path = "/{orderId}/cancel")
    public ResponseEntity<?> cancelOrder(Principal principal, @PathVariable(value = "orderId") Long orderId) {
        User user = this.userService.findByUsername(principal.getName());
        if (Optional.ofNullable(user).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        try {
            this.orderService.cancelOrder(user, orderId);

            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException | IllegalStateException e) {
            if (e instanceof EntityNotFoundException) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @PatchMapping(path = "/{orderId}/status")
    public ResponseEntity<?> updateOrderStatus(@PathVariable Long orderId, @RequestBody Map<String, String> params) {
        try {
            this.orderService.updateOrderStatus(orderId, params.get("status"));

            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException | IllegalStateException | IllegalArgumentException e) {
            if (e instanceof EntityNotFoundException) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }
}
