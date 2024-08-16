package com.security.Jwt_service.controller;

import com.security.Jwt_service.dto.request.SignInRequestDto;
import com.security.Jwt_service.dto.request.UserCreateDto;
import com.security.Jwt_service.dto.response.TokenResponseDto;
import com.security.Jwt_service.dto.response.UserResponseDto;
import com.security.Jwt_service.service.AuthService;
import com.security.Jwt_service.service.UserService;
import jakarta.servlet.http.HttpServlet;
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
public class AuthController {
    private final UserService userService;
    private final AuthService authService;
    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> registration(@RequestBody UserCreateDto createDto){
        return new ResponseEntity<>(userService.createUser(createDto), HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> login(@RequestBody SignInRequestDto signInRequestDto){
        return new ResponseEntity<>(authService.authenticate(signInRequestDto), HttpStatus.OK);
    }
    @PostMapping("/refresh")
    public ResponseEntity<TokenResponseDto> refresh(HttpServletRequest request){
        return new ResponseEntity<>(authService.refresh(request), HttpStatus.OK);
    }
}
