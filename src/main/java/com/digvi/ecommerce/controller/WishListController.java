package com.digvi.ecommerce.controller;

import com.digvi.ecommerce.exceptions.ProductException;
import com.digvi.ecommerce.model.Product;
import com.digvi.ecommerce.model.User;
import com.digvi.ecommerce.model.WishList;
import com.digvi.ecommerce.service.ProductService;
import com.digvi.ecommerce.service.UserService;
import com.digvi.ecommerce.service.WishListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/wishlist")
public class WishListController {

    private final WishListService wishListService;
    private final UserService userService;
    private final ProductService productService;

    @GetMapping()
    public ResponseEntity<WishList> getWishListByUser(
            @RequestHeader("Authorization") String jwt
    ) throws Exception {

        User user = userService.findUserByJwtToken(jwt);
        WishList wishList = wishListService.getWishListByUser(user);
        return ResponseEntity.ok(wishList);
    }

    @PostMapping("/addProduct/{productId}")
    public ResponseEntity<WishList> addProductToWishList(
            @PathVariable Long productId,
            @RequestHeader("Authorization")String jwt
    ) throws Exception {

        Product product = productService.findProductById(productId);
        User user = userService.findUserByJwtToken(jwt);
        WishList updatedWishList = wishListService.addProductToWishList(user, product);
        return ResponseEntity.ok(updatedWishList);
    }

}
