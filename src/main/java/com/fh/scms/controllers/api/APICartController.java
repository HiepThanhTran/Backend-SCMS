package com.fh.scms.controllers.api;

import com.fh.scms.dto.MessageResponse;
import com.fh.scms.dto.product.ProductRequestAddToCart;
import com.fh.scms.pojo.Cart;
import com.fh.scms.pojo.User;
import com.fh.scms.services.CartService;
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
        Optional.ofNullable(user).orElseThrow(() -> new EntityNotFoundException("không tìm thấy người dùng"));

        Cart cart = this.cartService.findCartByUser(user);

        return ResponseEntity.ok(this.cartService.getCartResponse(cart));
    }

    @PostMapping(path = "/product/add")
    public ResponseEntity<?> addProductToCart(Principal principal, @RequestBody ProductRequestAddToCart productRequestAddToCart) {
        User user = this.userService.findByUsername(principal.getName());
        Optional.ofNullable(user).orElseThrow(() -> new EntityNotFoundException("không tìm thấy người dùng"));

        this.cartService.addProductToCart(this.cartService.findCartByUser(user), productRequestAddToCart);

        return ResponseEntity.ok().build();
    }

    @PatchMapping(path = "/product/{productId}/update")
    public ResponseEntity<?> updateProductInCart(Principal principal, @PathVariable(value = "productId") Long productId,
                                                 @RequestBody Map<String, String> params) {
        User user = this.userService.findByUsername(principal.getName());
        Optional.ofNullable(user).orElseThrow(() -> new EntityNotFoundException("không tìm thấy người dùng"));

        this.cartService.updateProductInCart(this.cartService.findCartByUser(user), productId, params);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = "/product/{productId}/remove")
    public ResponseEntity<?> deleteProductFromCart(Principal principal, @PathVariable(value = "productId") Long productId) {
        User user = this.userService.findByUsername(principal.getName());
        Optional.ofNullable(user).orElseThrow(() -> new EntityNotFoundException("không tìm thấy người dùng"));

        this.cartService.deleteProductFromCart(this.cartService.findCartByUser(user), productId);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(path = "/product/clear")
    public ResponseEntity<?> clearCart(Principal principal) {
        User user = this.userService.findByUsername(principal.getName());
        Optional.ofNullable(user).orElseThrow(() -> new EntityNotFoundException("không tìm thấy người dùng"));

        this.cartService.clearCart(this.cartService.findCartByUser(user));

        return ResponseEntity.noContent().build();
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
