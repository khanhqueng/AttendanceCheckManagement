package com.security.Jwt_service.controller;

import com.security.Jwt_service.dto.request.user.SignInRequestDto;
import com.security.Jwt_service.dto.request.user.UserCreateDto;
import com.security.Jwt_service.dto.response.auth.TokenResponseDto;
import com.security.Jwt_service.dto.response.user.UserResponseDto;
import com.security.Jwt_service.service.AuthService;
import com.security.Jwt_service.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Auth Controller")
public class AuthController {
    private final UserService userService;
    private final AuthService authService;
    @Operation(summary = "Create user", description = "API for create new user")
    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> registration(@RequestBody UserCreateDto createDto){
        return new ResponseEntity<>(userService.createUser(createDto), HttpStatus.CREATED);
    }
    @Operation(summary = "Login", description = "API for supply access token, refresh token")
    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> login(@RequestBody SignInRequestDto signInRequestDto){
        return new ResponseEntity<>(authService.authenticate(signInRequestDto), HttpStatus.OK);
    }
    @Operation(summary = "Resupply access token", description = "API for recreate access token when old token expired")
    @PostMapping("/refresh")
    public ResponseEntity<TokenResponseDto> refresh(HttpServletRequest request){
        return new ResponseEntity<>(authService.refresh(request), HttpStatus.OK);
    }
}
