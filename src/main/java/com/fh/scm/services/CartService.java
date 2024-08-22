package com.fh.scm.services;

import com.fh.scm.dto.api.cart.CartDetailsResponse;
import com.fh.scm.dto.api.cart.CartResponse;
import com.fh.scm.dto.api.product.ProductRequestAddToCart;
import com.fh.scm.pojo.Cart;
import com.fh.scm.pojo.CartDetails;
import com.fh.scm.pojo.User;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

public interface CartService {

    CartResponse getCartResponse(Cart cart);

    CartDetailsResponse getCartDetailsResponse(CartDetails cartDetails);

    Cart getCart(User user);

    void addProductToCart(Cart cart, ProductRequestAddToCart productRequestAddToCart);

    void updateProductInCart(Cart cart, Long productId, Map<String, String> params);

    void deleteProductFromCart(Cart cart, Long productId);

    void clearCart(Cart cart);
}
