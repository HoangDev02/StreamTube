package com.StreamTube.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.StreamTube.dtos.AuthRequest;
import com.StreamTube.dtos.JwtResponse;
import com.StreamTube.dtos.RefreshTokenRequest;
import com.StreamTube.dtos.UserBasicInfor;
import com.StreamTube.dtos.UserDTO;
import com.StreamTube.jwt.JwtService;
import com.StreamTube.jwt.RefreshTokenService;
import com.StreamTube.mappers.UserMapper;
import com.StreamTube.models.RefreshToken;
import com.StreamTube.models.User;
import com.StreamTube.services.UserService;

@RestController
@RequestMapping("/")
public class AuthenticateController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        try {
            // Xác thực người dùng
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

            if (authentication.isAuthenticated()) {
                // Tạo access token (30 giây hết hạn)
                String accessToken = jwtService.generateToken(authRequest.getUsername());

                // Tạo refresh token (10 ngày hết hạn)
                RefreshToken refreshToken = refreshTokenService.createRefreshToken(authRequest.getUsername());

                // Tạo đối tượng JwtResponse hoặc một Map để chứa cả accessToken và refreshToken
                Map<String, String> tokens = new HashMap<>();
                tokens.put("accessToken", accessToken);
                tokens.put("refreshToken", refreshToken.getRefreshToken());

                return ResponseEntity.ok(tokens);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed");
            }
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> createUser(@RequestBody UserDTO userDTO) {
        try {
            User user = userMapper.toModel(userDTO);
            userService.createUser(user);
            UserBasicInfor createdUser = userMapper.toBasicDTO(user);
            // emailService.sendEmailForAccountCreated(user); // Uncomment if email service is configured
            return ResponseEntity.ok(createdUser);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping("/refreshToken")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        // First, ensure that the request body is not null
        if (refreshTokenRequest == null || refreshTokenRequest.getRefreshToken() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Refresh token is required");
        }

        // Then, attempt to find the refresh token and validate it
        return refreshTokenService.findByToken(refreshTokenRequest.getRefreshToken())
            .map(refreshTokenService::verifyExpiration)
            .map(refreshToken -> {
                if (refreshToken.getUser() == null) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid refresh token");
                }
                String username = refreshToken.getUser().getUsername();
                int userId = refreshToken.getUser().getId();

                // Tạo access token mới
                String newAccessToken = jwtService.generateToken(username);

                // Tạo và trả về JwtResponse
                JwtResponse jwtResponse = new JwtResponse(newAccessToken, refreshToken.getRefreshToken(), username, userId);
                return ResponseEntity.ok(jwtResponse);
            })
            .orElseGet(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Refresh token not found or expired"));
    }


}
