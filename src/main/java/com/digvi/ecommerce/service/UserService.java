package com.digvi.ecommerce.service;

import com.digvi.ecommerce.model.User;

public interface UserService {
    User findUserByJwtToken(String jwt) throws Exception;
    User findUserByEmail(String email) throws Exception;
}
