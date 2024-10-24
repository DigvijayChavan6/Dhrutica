package com.digvi.ecommerce.controller;

import com.digvi.ecommerce.response.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @GetMapping
    public ApiResponse HomeControllerHandler(){
        ApiResponse res = new ApiResponse();
        res.setMessage("Welcome to Ecommerce");
        return res;
    }
}
