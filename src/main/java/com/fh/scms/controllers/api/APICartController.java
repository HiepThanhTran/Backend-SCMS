package com.fh.scms.controllers.api;

import com.fh.scms.dto.MessageResponse;
import com.fh.scms.dto.product.ProductRequestAddToCart;
import com.fh.scms.pojo.Cart;
import com.fh.scms.pojo.User;
import com.fh.scms.services.CartService;
import com.fh.scms.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/cart", produces = "application/json; charset=UTF-8")
public class APICartController {

    private final CartService cartService;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<?> getCart(Principal principal) {
        User user = this.userService.findByUsername(principal.getName());
        if (Optional.ofNullable(user).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Cart cart = this.cartService.findCartByUser(user);
        return ResponseEntity.ok(this.cartService.getCartResponse(cart));
    }

    @PostMapping(path = "/product/add")
    public ResponseEntity<?> addProductToCart(Principal principal, @RequestBody ProductRequestAddToCart productRequestAddToCart) {
        User user = this.userService.findByUsername(principal.getName());
        if (Optional.ofNullable(user).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        this.cartService.addProductToCart(this.cartService.findCartByUser(user), productRequestAddToCart);
        return ResponseEntity.ok().build();
    }

    @PatchMapping(path = "/product/{productId}/update")
    public ResponseEntity<?> updateProductInCart(Principal principal, @PathVariable(value = "productId") Long productId,
                                                 @RequestBody Map<String, String> params) {
        User user = this.userService.findByUsername(principal.getName());
        if (Optional.ofNullable(user).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        try {
            this.cartService.updateProductInCart(this.cartService.findCartByUser(user), productId, params);

            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            List<MessageResponse> errorMessages = List.of(new MessageResponse(e.getMessage()));

            return ResponseEntity.badRequest().body(errorMessages);
        }
    }

    @DeleteMapping(path = "/product/{productId}/delete")
    public ResponseEntity<?> deleteProductFromCart(Principal principal, @PathVariable(value = "productId") Long productId) {
        User user = this.userService.findByUsername(principal.getName());
        if (Optional.ofNullable(user).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        try {
            this.cartService.deleteProductFromCart(this.cartService.findCartByUser(user), productId);

            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            List<MessageResponse> errorMessages = List.of(new MessageResponse(e.getMessage()));

            return ResponseEntity.badRequest().body(errorMessages);
        }
    }

    @DeleteMapping(path = "/product/clear")
    public ResponseEntity<?> clearCart(Principal principal) {
        User user = this.userService.findByUsername(principal.getName());
        if (Optional.ofNullable(user).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        this.cartService.clearCart(this.cartService.findCartByUser(user));
        return ResponseEntity.noContent().build();
    }
}
