package com.booking.auth.controller;

import com.booking.auth.dto.AuthResponse;
import com.booking.auth.dto.LoginRequest;
import com.booking.auth.dto.RegisterRequest;
import com.booking.auth.service.AuthService;
import com.booking.common.response.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public BaseResponse<?> register(@RequestBody RegisterRequest registerRequest) {
        return authService.register(registerRequest);
    }

    @PostMapping("/login")
    public BaseResponse<?> login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }
}
