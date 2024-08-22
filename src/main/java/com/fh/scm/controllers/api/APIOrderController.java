package com.fh.scm.controllers.api;

import com.fh.scm.dto.MessageResponse;
import com.fh.scm.dto.api.order.OrderRequest;
import com.fh.scm.dto.api.order.OrderResponse;
import com.fh.scm.pojo.User;
import com.fh.scm.services.CartService;
import com.fh.scm.services.OrderService;
import com.fh.scm.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/orders", produces = "application/json; charset=UTF-8")
public class APIOrderController {

    private final CartService cartService;
    private final OrderService orderService;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<?> listOrders(Principal principal) {
        User user = this.userService.getByUsername(principal.getName());

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        List<OrderResponse> orderList = this.orderService.getAllOrderResponse(Map.of("userId", user.getId().toString()));

        return ResponseEntity.ok(orderList);
    }

    @PostMapping(path = "/checkout")
    public ResponseEntity<?> checkout(Principal principal, @RequestBody @Valid OrderRequest orderRequest) {
        User user = this.userService.getByUsername(principal.getName());

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        try {
            this.orderService.checkout(user, orderRequest);
        } catch (IllegalStateException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping(path = "/{orderId}/cancel")
    public ResponseEntity<?> cancelOrder(Principal principal, @PathVariable(value = "orderId") Long orderId) {
        User user = this.userService.getByUsername(principal.getName());

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        try {
            this.orderService.cancelOrder(user, orderId);

            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException | IllegalStateException e) {
            if (e instanceof EntityNotFoundException) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse(e.getMessage()));
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
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }
}
