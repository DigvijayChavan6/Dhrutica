package com.digvi.ecommerce.service.impl;

import com.digvi.ecommerce.model.Cart;
import com.digvi.ecommerce.model.CartItems;
import com.digvi.ecommerce.model.Product;
import com.digvi.ecommerce.model.User;
import com.digvi.ecommerce.repository.CartItemRepository;
import com.digvi.ecommerce.repository.CartRepository;
import com.digvi.ecommerce.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    @Override
    public CartItems addCartItem(User user, Product product, String size, int quantity) {
        Cart cart = this.findUserCart(user);

        CartItems isPresent = cartItemRepository.findByCartAndProductAndSize(cart, product, size);

        if(isPresent == null){
            CartItems  cartItems = new CartItems();
            cartItems.setCart(cart);
            cartItems.setUserId(user.getId());
            cartItems.setQuantity(quantity);
            cartItems.setProduct(product);
            cartItems.setMrpPrice(quantity*product.getMrpPrice());
            cartItems.setSellingPrice(quantity*product.getSellingPrice());
            cart.getCartItems().add(cartItems);
            return cartItemRepository.save(cartItems);
        }
        return isPresent;
    }

    @Override
    public Cart findUserCart(User user) {
        Cart cart = cartRepository.findByUserId(user.getId());

        double totalPrice = 0;
        double totalDiscountedPrice = 0;
        int totalItem = 0;

        for(CartItems cartItems : cart.getCartItems()){
            totalItem += cartItems.getQuantity();
            totalPrice += cartItems.getMrpPrice();
            totalDiscountedPrice += cartItems.getSellingPrice();
        }

        cart.setTotalItem(totalItem);
        cart.setTotalMrpPrice(totalPrice);
        cart.setTotalSellingPrice(totalDiscountedPrice);
        cart.setDiscount(this.calculateDiscountPercentage(totalPrice, totalDiscountedPrice));

        return cart;
    }

    private double calculateDiscountPercentage(double mrpPrice, double sellingPrice){
        if(mrpPrice <= 0){
            return 0;
        }
        double discount = mrpPrice - sellingPrice;
        return (discount/mrpPrice)*100;
    }

}
