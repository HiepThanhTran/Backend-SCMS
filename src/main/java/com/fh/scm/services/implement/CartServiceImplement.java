package com.fh.scm.services.implement;

import com.fh.scm.dto.api.cart.CartDetailsResponse;
import com.fh.scm.dto.api.cart.CartResponse;
import com.fh.scm.dto.api.product.ProductRequestAddToCart;
import com.fh.scm.pojo.Cart;
import com.fh.scm.pojo.CartDetails;
import com.fh.scm.pojo.Product;
import com.fh.scm.pojo.User;
import com.fh.scm.repository.CartDetailsRepository;
import com.fh.scm.repository.CartRepository;
import com.fh.scm.services.CartService;
import com.fh.scm.services.ProductService;
import com.fh.scm.util.Utils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
@Transactional
public class CartServiceImplement implements CartService {

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartDetailsRepository cartDetailsRepository;
    @Autowired
    private ProductService productService;

    @Override
    public CartResponse getCartResponse(@NotNull Cart cart) {
        return CartResponse.builder().cartDetails(Optional.ofNullable(cart.getCartDetailsSet())
                .orElseGet(Set::of)
                .stream()
                .map(this::getCartDetailsResponse)
                .collect(Collectors.toSet())).build();
    }

    @Override
    public CartDetailsResponse getCartDetailsResponse(@NotNull CartDetails cartDetails) {
        return CartDetailsResponse.builder()
                .id(cartDetails.getId())
                .quantity(cartDetails.getQuantity())
                .unitPrice(cartDetails.getUnitPrice())
                .product(this.productService.getProductResponse(cartDetails.getProduct()))
                .build();
    }

    @Override
    public Cart getCart(@NotNull User user) {
        return Optional.ofNullable(user.getCart()).orElseGet(() -> {
            Cart newCart = new Cart();
            newCart.setUser(user);
            this.cartRepository.insert(newCart);
            return newCart;
        });
    }

    @Override
    public void addProductToCart(@NotNull Cart cart, @NotNull ProductRequestAddToCart productRequestAddToCart) {
        Product product = this.productService.get(productRequestAddToCart.getProductId());
        CartDetails existingCartDetails = Optional.ofNullable(cart.getCartDetailsSet()).orElseGet(Set::of).stream()
                .filter(cd -> cd.getProduct().getId().equals(product.getId()))
                .findFirst()
                .orElse(null);

        if (existingCartDetails != null) {
            existingCartDetails.setQuantity(existingCartDetails.getQuantity() + productRequestAddToCart.getQuantity());
            this.cartDetailsRepository.merge(existingCartDetails);
        } else {
            CartDetails cartDetails = CartDetails.builder()
                    .cart(cart)
                    .product(product)
                    .unitPrice(product.getPrice())
                    .quantity(productRequestAddToCart.getQuantity())
                    .build();
            this.cartDetailsRepository.persist(cartDetails);
        }
    }

    @Override
    public void updateProductInCart(@NotNull Cart cart, Long productId, @NotNull Map<String, String> params) {
        CartDetails cartDetails = Optional.ofNullable(cart.getCartDetailsSet()).orElseGet(Set::of).stream()
                .filter(cd -> cd.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Sản phẩm không tồn tại trong giỏ hàng."));

        params.forEach((key, value) -> {
            try {
                Field cartDetailsField = CartDetails.class.getDeclaredField(key);
                cartDetailsField.setAccessible(true);
                Object convertedValue = Utils.convertValue(cartDetailsField.getType(), value);
                cartDetailsField.set(cartDetails, convertedValue);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                Logger.getLogger(UserServiceImplement.class.getName()).log(Level.SEVERE, null, e);
            }
        });

        this.cartDetailsRepository.merge(cartDetails);
    }

    @Override
    public void deleteProductFromCart(@NotNull Cart cart, Long productId) {
        CartDetails cartDetails = Optional.ofNullable(cart.getCartDetailsSet()).orElseGet(Set::of).stream()
                .filter(cd -> cd.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Sản phẩm không tồn tại trong giỏ hàng."));

        this.cartDetailsRepository.delete(cartDetails.getId());
    }

    @Override
    public void clearCart(@NotNull Cart cart) {
        cart.getCartDetailsSet().forEach(cd -> this.cartDetailsRepository.delete(cd.getId()));
    }
}
