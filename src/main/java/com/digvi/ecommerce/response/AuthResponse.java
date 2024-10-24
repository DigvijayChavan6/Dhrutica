package com.digvi.ecommerce.response;

import com.digvi.ecommerce.domain.USER_ROLE;
import lombok.Data;

@Data
public class AuthResponse {
    private String jwt;
    private String message;
    private USER_ROLE role;
}
