package com.digvi.ecommerce.service;

import com.digvi.ecommerce.domain.USER_ROLE;
import com.digvi.ecommerce.request.LoginRequest;
import com.digvi.ecommerce.response.AuthResponse;
import com.digvi.ecommerce.response.SignupRequest;

public interface AuthService {

    void sentLoginOtp(String email, USER_ROLE role) throws Exception;
    String createUser(SignupRequest req) throws Exception;
    AuthResponse signing(LoginRequest req);
}
