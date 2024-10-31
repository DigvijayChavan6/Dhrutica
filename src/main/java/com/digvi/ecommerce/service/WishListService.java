package com.digvi.ecommerce.service;

import com.digvi.ecommerce.model.Product;
import com.digvi.ecommerce.model.User;
import com.digvi.ecommerce.model.WishList;


public interface WishListService {
    WishList createWishList(User user);
    WishList getWishListByUser(User user);
    WishList addProductToWishList(User user, Product product);
}
