package com.digvi.ecommerce.response;

import lombok.Data;

@Data
public class ApiResponse {
    private String message;

    public void setMessage(String welcomeToEcommerce) {
        message = welcomeToEcommerce;
    }

    public String getMessage() {
        return message;
    }
}
