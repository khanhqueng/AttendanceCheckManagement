package com.security.Jwt_service.controller;

import com.security.Jwt_service.config.security.CustomUserDetails;
import com.security.Jwt_service.dto.request.user.UserCreateDto;
import com.security.Jwt_service.dto.request.user.UserUpdatePasswordDto;
import com.security.Jwt_service.dto.request.user.UserUpdateVerify;
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

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name = "User Controller")
public class UserController {
    private final UserService userService;
    @Operation(summary = "Get user by its id (id is store in token when login)", description = "API for get user by its id")
    @GetMapping("/{user_id}")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable(name = "user_id") Long id){
        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }
    @Operation(summary = "Update user email", description = "API for update email of user")
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
    @Operation(summary = "Create user by role-name ( Student or Teacher )", description = "API for create user by their role")
    @PostMapping
    public ResponseEntity<UserResponseFactory> createUser(@RequestBody @Valid  UserCreateDto createDto){
        UserCreateMethod userCreateMethod= userService.createUserMethod(createDto);
        return new ResponseEntity<>(userCreateMethod.createUser(createDto), HttpStatus.CREATED);
    }
    @Operation(summary = "Generate code to reset password", description = "API for generate reset code")
    @PostMapping("/update-password/code")
    public ResponseEntity<CompletableFuture<String>> createCode(@RequestBody @Valid UserUpdatePasswordDto updatePasswordDto){
        return new ResponseEntity<>(userService.genCodeForChangePassword(updatePasswordDto), HttpStatus.OK);
    }
    @Operation(summary = "Update password with code", description = "API for update password with code generated")
    @PutMapping("/update-password")
    public ResponseEntity<UserResponseDto> updatePassword(@RequestBody @Valid UserUpdateVerify updateVerify){
        return new ResponseEntity<>(userService.changePassword(updateVerify), HttpStatus.OK);
    }
    @Operation(summary = "Verify code", description = "API for verify code")
    @PostMapping("/update-password/verify-code/{code}")
    public ResponseEntity<String> verifyCode(@RequestBody @Valid UserUpdatePasswordDto dto,@PathVariable(name = "code") String code ){
        return userService.verifyCode(dto, code)
                ? new ResponseEntity<>("Code is verified", HttpStatus.OK)
                : new ResponseEntity<>("Code is incorrect", HttpStatus.BAD_REQUEST);
    }
}
