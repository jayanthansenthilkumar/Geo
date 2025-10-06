package com.georeport.service;

import com.georeport.config.JwtTokenProvider;
import com.georeport.dto.LoginRequest;
import com.georeport.dto.LoginResponse;
import com.georeport.dto.PublicUserRegistrationRequest;
import com.georeport.entity.PublicUser;
import com.georeport.entity.User;
import com.georeport.repository.PublicUserRepository;
import com.georeport.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PublicUserRepository publicUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Transactional
    public LoginResponse loginUser(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Invalid username or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid username or password");
        }

        if (!user.getIsActive()) {
            throw new RuntimeException("Account is inactive");
        }

        // Update last login
        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);

        String token = jwtTokenProvider.generateToken(user.getUsername(), user.getRole(), user.getId());
        
        return new LoginResponse(token, user.getId(), user.getUsername(), 
                user.getRole(), user.getFullName(), user.getEmail());
    }

    @Transactional
    public LoginResponse loginPublicUser(LoginRequest request) {
        PublicUser user = publicUserRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Invalid username or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid username or password");
        }

        if (!user.getIsActive()) {
            throw new RuntimeException("Account is inactive");
        }

        String token = jwtTokenProvider.generateToken(user.getUsername(), "public", user.getId());
        
        return new LoginResponse(token, user.getId(), user.getUsername(), 
                "public", user.getFullName(), user.getEmail());
    }

    @Transactional
    public PublicUser registerPublicUser(PublicUserRegistrationRequest request) {
        if (publicUserRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        if (publicUserRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        PublicUser user = new PublicUser();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setAddress(request.getAddress());
        user.setIsActive(true);

        return publicUserRepository.save(user);
    }

    @Transactional
    public User createUser(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        if (user.getEmail() != null && userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setIsActive(true);
        return userRepository.save(user);
    }
}
