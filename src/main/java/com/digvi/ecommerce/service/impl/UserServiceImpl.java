package com.digvi.ecommerce.service.impl;

import com.digvi.ecommerce.config.JwtProvider;
import com.digvi.ecommerce.model.User;
import com.digvi.ecommerce.repository.UserRepository;
import com.digvi.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    @Override
    public User findUserByJwtToken(String jwt) throws Exception {
        String email = jwtProvider.getEmailFromJwtToken(jwt);
        return this.findUserByEmail(email);
    }

    @Override
    public User findUserByEmail(String email) throws Exception {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new Exception("User is not found with this email-"+email);
        }
        return user;
    }
}
