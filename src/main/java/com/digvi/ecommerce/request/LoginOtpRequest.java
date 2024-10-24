package com.digvi.ecommerce.request;

import com.digvi.ecommerce.domain.USER_ROLE;
import lombok.Data;

@Data
public class LoginOtpRequest {
    private String email;
    private String otp;
    private USER_ROLE role;
}
