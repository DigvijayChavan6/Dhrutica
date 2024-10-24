package com.digvi.ecommerce.service;

import com.digvi.ecommerce.domain.AccountStatus;
import com.digvi.ecommerce.model.Seller;

import java.util.List;

public interface SellerService {

    Seller createSeller(Seller seller) throws Exception;
    Seller getSellerProfile(String jwt) throws Exception;
    Seller getSellerById(Long id) throws Exception;
    Seller getSellerByEmail(String email) throws Exception;
    List<Seller> getAllSellers(AccountStatus accountStatus);
    Seller updateSeller(Long id, Seller seller) throws Exception;
    void deleteSeller(Long id) throws Exception;
    Seller verifyEmail(String email, String otp) throws Exception;
    Seller updateSellerAccountStatus(Long sellerId, AccountStatus accountStatus) throws Exception;

}
