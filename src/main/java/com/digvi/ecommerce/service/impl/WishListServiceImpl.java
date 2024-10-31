package com.digvi.ecommerce.service.impl;

import com.digvi.ecommerce.model.Product;
import com.digvi.ecommerce.model.User;
import com.digvi.ecommerce.model.WishList;
import com.digvi.ecommerce.repository.WishListRepository;
import com.digvi.ecommerce.service.WishListService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WishListServiceImpl implements WishListService {

    private final WishListRepository wishListRepository;

    @Override
    public WishList createWishList(User user) {
        WishList wishList = new WishList();
        wishList.setUser(user);
        return wishListRepository.save(wishList);
    }

    @Override
    public WishList getWishListByUser(User user) {
        WishList wishList = wishListRepository.findByUserId(user.getId());
        if(wishList == null){
            wishList = createWishList(user);
        }
        return wishList;
    }

    @Override
    public WishList addProductToWishList(User user, Product product) {
        WishList wishList = getWishListByUser(user);

        if(wishList.getProduct().contains(product)){
            wishList.getProduct().remove(product);
        }else{
            wishList.getProduct().add(product);
        }
        return wishListRepository.save(wishList);
    }
}
