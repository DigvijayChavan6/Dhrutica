package com.digvi.ecommerce.service;

import com.digvi.ecommerce.model.Cart;
import com.digvi.ecommerce.model.CartItems;
import com.digvi.ecommerce.model.Product;
import com.digvi.ecommerce.model.User;

public interface CartService {

    public CartItems addCartItem(
            User user,
            Product product,
            String size,
            int quantity
    );
    public Cart findUserCart(User user);
}
