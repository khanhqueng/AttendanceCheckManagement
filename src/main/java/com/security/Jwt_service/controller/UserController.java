package com.security.Jwt_service.controller;

import com.security.Jwt_service.config.security.CustomUserDetails;
import com.security.Jwt_service.dto.request.user.UserCreateDto;
import com.security.Jwt_service.dto.response.user.UserResponseDto;
import com.security.Jwt_service.dto.response.user.UserResponseFactory;
import com.security.Jwt_service.service.UserService;
import com.security.Jwt_service.service.factorymethod.UserCreateMethod;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name = "User Controller")
public class UserController {
    private final UserService userService;
    @Operation(summary = "Get user by id", description = "API for get user by id")
    @GetMapping
    public ResponseEntity<UserResponseDto> getUser(Authentication authentication){
        Long userId= ((CustomUserDetails ) authentication.getPrincipal()).getId();
        return new ResponseEntity<>(userService.getUserById(userId), HttpStatus.OK);
    }
    @Operation(summary = "Update user", description = "API for update existing user")
    @PutMapping
    public ResponseEntity<UserResponseDto> updateUser(Authentication authentication, @RequestParam(name = "email") String email){
        Long userId= ((CustomUserDetails ) authentication.getPrincipal()).getId();
        return new ResponseEntity<>(userService.updateUser(userId,email), HttpStatus.OK);
    }
    @Operation(summary = "Change user role", description = "API for change user role")
    @PutMapping("/role/{id}")
    public ResponseEntity<UserResponseDto> changeRoleUser( @PathVariable(name = "id") Long userId, @RequestParam(name = "role_name") String roleName ){
        return new ResponseEntity<>(userService.changeRole(userId,roleName), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserResponseFactory> createUser(@RequestBody @Valid  UserCreateDto createDto){
        UserCreateMethod userCreateMethod= userService.createUserMethod(createDto);
        return new ResponseEntity<>(userCreateMethod.createUser(createDto), HttpStatus.CREATED);
    }

}
