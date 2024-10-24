package com.digvi.ecommerce.service.impl;

import com.digvi.ecommerce.config.JwtProvider;
import com.digvi.ecommerce.domain.USER_ROLE;
import com.digvi.ecommerce.model.Cart;
import com.digvi.ecommerce.model.Seller;
import com.digvi.ecommerce.model.User;
import com.digvi.ecommerce.model.VerificationCode;
import com.digvi.ecommerce.repository.CartRepository;
import com.digvi.ecommerce.repository.SellerRepository;
import com.digvi.ecommerce.repository.UserRepository;
import com.digvi.ecommerce.repository.VerificationCodeRepository;
import com.digvi.ecommerce.request.LoginRequest;
import com.digvi.ecommerce.response.AuthResponse;
import com.digvi.ecommerce.request.SignupRequest;
import com.digvi.ecommerce.service.AuthService;
import com.digvi.ecommerce.service.EmailService;
import com.digvi.ecommerce.utils.OtpUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final SellerRepository sellerRepository;
    private final PasswordEncoder passwordEncoder;
    private final CartRepository cartRepository;
    private final JwtProvider jwtProvider;
    private final VerificationCodeRepository verificationCodeRepository;
    private final EmailService emailService;
    private final CustomUserServiceImpl customUserService;
    private static final String SELLER_PREFIX = "seller_";
    @Override
    public void sentSignUpLoginOtp(String email, USER_ROLE role) throws Exception {
        String SIGNING_PREFIX = "signing_";

        if(email.startsWith(SIGNING_PREFIX)){
            email=email.substring(SIGNING_PREFIX.length());

            if(role.equals(USER_ROLE.ROLE_SELLER)){
                Seller seller=sellerRepository.findByEmail(email);
                if(seller == null){
                    throw new Exception("seller is not exists...");
                }
            }else {
                User user=userRepository.findByEmail(email);
                if(user == null){
                    throw new Exception("user is not exists...");
                }
            }
        }
        // else { user is exists so why signup }
        VerificationCode isExist=verificationCodeRepository.findByEmail(email);
        if(isExist != null){
            verificationCodeRepository.delete(isExist);
        }
        String otp = OtpUtil.generateOtp();

        VerificationCode verificationCode=new VerificationCode();
        verificationCode.setOtp(otp);
        verificationCode.setEmail(email);
        verificationCodeRepository.save(verificationCode);

        String subject = "Signup/Login OTP Verification";
        String text = "Dear user,\nPlease use the following OTP to complete your signup or login process.";

        emailService.sendVerificationOtpEmail(email, otp, subject, text);
    }

    @Override
    public String createUser(SignupRequest req) throws Exception {
        VerificationCode verificationCode = verificationCodeRepository.findByEmail(req.getEmail());

        if(verificationCode == null || !verificationCode.getOtp().equals(req.getOtp())){
            throw new Exception("Wrong otp...");
        }

        User user = userRepository.findByEmail(req.getEmail());

        if(user == null){
            User createdUser = new User();
            createdUser.setEmail(req.getEmail());
            createdUser.setFullName(req.getFullName());
            createdUser.setRole(USER_ROLE.ROLE_CUSTOMER);
            createdUser.setMobile("9860904442");
            createdUser.setPassword(passwordEncoder.encode(req.getOtp()));
            user = userRepository.save(createdUser);

            Cart cart = new Cart();
            cart.setUser(user);
            cartRepository.save(cart);
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(USER_ROLE.ROLE_CUSTOMER.toString()));

        Authentication authentication = new UsernamePasswordAuthenticationToken(req.getEmail(), null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return jwtProvider.generateToken(authentication);
    }

    @Override
    public AuthResponse signing(LoginRequest req) {
        String username = req.getEmail();
        String otp = req.getOtp();

        System.out.println(req);

        Authentication authentication = authenticate(username, otp);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token=jwtProvider.generateToken(authentication);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(token);
        authResponse.setMessage("Login successfully");

        Collection<? extends GrantedAuthority> authorities=authentication.getAuthorities();
        String roleName=authorities.isEmpty() ? null : authorities.iterator().next().getAuthority();

        authResponse.setRole(USER_ROLE.valueOf(roleName));
        return authResponse;
    }

    private Authentication authenticate(String username, String otp) {
        UserDetails userDetails=customUserService.loadUserByUsername(username);
        if(username.startsWith(SELLER_PREFIX)){
            username = username.substring(SELLER_PREFIX.length());
        }
        VerificationCode verificationCode=verificationCodeRepository.findByEmail(username);
        System.out.println(userDetails +"\n"+
                username+"\n"+verificationCode+"\n"+otp);
        if(userDetails == null || verificationCode == null || !verificationCode.getOtp().equals(otp)){
            throw new BadCredentialsException("Invalid username or otp...");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
