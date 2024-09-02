package com.fh.scms.services;

import com.fh.scms.dto.cart.CartDetailsResponse;
import com.fh.scms.dto.product.ProductRequestAddToCart;
import com.fh.scms.pojo.Cart;
import com.fh.scms.pojo.CartDetails;
import com.fh.scms.pojo.User;

import java.util.Map;

public interface CartService {

    Map<Long, CartDetailsResponse> getCartResponse(Cart cart);

    CartDetailsResponse getCartDetailsResponse(CartDetails cartDetails);

    Cart findCartByUser(User user);

    void addProductToCart(Cart cart, ProductRequestAddToCart productRequestAddToCart);

    void updateProductInCart(Cart cart, Long productId, Map<String, String> params);

    void deleteProductFromCart(Cart cart, Long productId);
}
