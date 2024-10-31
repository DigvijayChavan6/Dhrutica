package com.digvi.ecommerce.service.impl;

import com.digvi.ecommerce.model.CartItems;
import com.digvi.ecommerce.model.User;
import com.digvi.ecommerce.repository.CartItemRepository;
import com.digvi.ecommerce.service.CartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {
    private final CartItemRepository cartItemRepository;

    @Override
    public CartItems updateCartItem(Long userId, Long cartItemId, CartItems cartItems) throws Exception {
        CartItems items = findCartItemById(cartItemId);

        User cartItemUser = items.getCart().getUser();

        if(cartItemUser.getId().equals(userId)){
            items.setQuantity(cartItems.getQuantity());
            items.setMrpPrice(items.getQuantity()*items.getProduct().getMrpPrice());
            items.setSellingPrice(items.getQuantity()*items.getProduct().getSellingPrice());
            return cartItemRepository.save(items);
        }
        throw new Exception("You can't update this cartItem, this is not yours");
    }

    @Override
    public void removeCartItem(Long userId, Long cartItemId) throws Exception {
        CartItems items = findCartItemById(cartItemId);

        User cartItemUser = items.getCart().getUser();

        if(cartItemUser.getId().equals(userId)){
            cartItemRepository.delete(items);
        }else{
            throw new Exception("You can't delete this cartItem, this is not yours");
        }
    }

    @Override
    public CartItems findCartItemById(Long cartItemId) throws Exception {
        return cartItemRepository.findById(cartItemId).orElseThrow(()->
                new Exception("CartItem not found with id "+cartItemId));
    }
}
