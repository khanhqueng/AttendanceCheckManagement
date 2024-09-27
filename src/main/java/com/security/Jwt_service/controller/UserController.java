package com.security.Jwt_service.controller;

import com.security.Jwt_service.config.security.CustomUserDetails;
import com.security.Jwt_service.dto.response.user.UserResponseDto;
import com.security.Jwt_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @GetMapping
    public ResponseEntity<UserResponseDto> getUser(Authentication authentication){
        Long userId= ((CustomUserDetails ) authentication.getPrincipal()).getId();
        return new ResponseEntity<>(userService.getUserById(userId), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<UserResponseDto> updateUser(Authentication authentication, @RequestParam(name = "email") String email){
        Long userId= ((CustomUserDetails ) authentication.getPrincipal()).getId();
        return new ResponseEntity<>(userService.updateUser(userId,email), HttpStatus.OK);
    }



}
