package com.digvi.ecommerce.repository;

import com.digvi.ecommerce.model.Cart;
import com.digvi.ecommerce.model.CartItems;
import com.digvi.ecommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItems, Long> {
    CartItems findByCartAndProductAndSize(Cart cart, Product product, String size);
}
