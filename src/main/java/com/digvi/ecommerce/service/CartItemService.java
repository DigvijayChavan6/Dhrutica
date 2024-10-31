package com.digvi.ecommerce.service;

import com.digvi.ecommerce.model.CartItems;

public interface CartItemService {
    CartItems updateCartItem(Long userId, Long cartItemId, CartItems cartItems) throws Exception;
    void removeCartItem(Long userId, Long cartItemId) throws Exception;
    CartItems findCartItemById(Long cartItemId) throws Exception;
}
