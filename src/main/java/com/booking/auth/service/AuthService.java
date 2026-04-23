package com.booking.auth.service;

import com.booking.auth.dto.AuthResponse;
import com.booking.auth.dto.LoginRequest;
import com.booking.auth.dto.RegisterRequest;
import com.booking.auth.jwt.JwtService;
import com.booking.common.enums.Role;
import com.booking.common.exception.AppException;
import com.booking.common.response.BaseResponse;
import com.booking.domain.entity.User;
import com.booking.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    //REGISTER
    public BaseResponse<?> register(RegisterRequest request){
        if (userRepository.findByEmail(request.getEmail()).isPresent()){
            throw new AppException(400,"Email already exists");
        }
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);
        userRepository.save(user);

        String token = jwtService.generateToken(user);
        AuthResponse authResponse = new AuthResponse(token);
        return BaseResponse.success(authResponse);
    }

    //LOGIN
    public BaseResponse<?> login(LoginRequest request){
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new AppException(404,"User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new AppException(400,"Wrong password");
        }
        String token = jwtService.generateToken(user);
        AuthResponse authResponse = new AuthResponse(token);
        return BaseResponse.success(authResponse);
    }

}
