package com.digvi.ecommerce.controller;

import com.digvi.ecommerce.domain.USER_ROLE;
import com.digvi.ecommerce.model.User;
import com.digvi.ecommerce.model.VerificationCode;
import com.digvi.ecommerce.repository.UserRepository;
import com.digvi.ecommerce.request.LoginOtpRequest;
import com.digvi.ecommerce.request.LoginRequest;
import com.digvi.ecommerce.response.ApiResponse;
import com.digvi.ecommerce.response.AuthResponse;
import com.digvi.ecommerce.response.SignupRequest;
import com.digvi.ecommerce.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody SignupRequest req) throws Exception {

        String jwt = authService.createUser(req);
        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("Register success");
        authResponse.setRole(USER_ROLE.ROLE_CUSTOMER);

        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/sent/login-signup-otp")
    public ResponseEntity<ApiResponse> sentOtpHandler(@RequestBody LoginOtpRequest req) throws Exception {
        authService.sentLoginOtp(req.getEmail(), req.getRole());
        ApiResponse apiResponse = new ApiResponse();

        apiResponse.setMessage("otp sent successfully");

        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/signing")
    public ResponseEntity<AuthResponse> loginHandler(@RequestBody LoginRequest req) throws Exception {
        AuthResponse authResponse = authService.signing(req);

        return ResponseEntity.ok(authResponse);
    }
}
