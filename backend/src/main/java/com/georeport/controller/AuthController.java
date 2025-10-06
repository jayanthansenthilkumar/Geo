package com.georeport.controller;

import com.georeport.dto.ApiResponse;
import com.georeport.dto.LoginRequest;
import com.georeport.dto.LoginResponse;
import com.georeport.dto.PublicUserRegistrationRequest;
import com.georeport.entity.PublicUser;
import com.georeport.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            LoginResponse response = authService.loginUser(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }

    @PostMapping("/public/login")
    public ResponseEntity<?> loginPublic(@RequestBody LoginRequest request) {
        try {
            LoginResponse response = authService.loginPublicUser(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }

    @PostMapping("/public/register")
    public ResponseEntity<?> registerPublic(@RequestBody PublicUserRegistrationRequest request) {
        try {
            PublicUser user = authService.registerPublicUser(request);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse(true, "Registration successful", user));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }
}
