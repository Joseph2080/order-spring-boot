package org.chitsa.orderservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.chitsa.orderservice.dto.LoginRequestDto;
import org.chitsa.orderservice.dto.UserRegisterDto;
import org.chitsa.orderservice.exception.AuthenticationException;
import org.chitsa.orderservice.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User API", description = "API for managing users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signUp")
    @Operation(summary = "Create a new user", description = "Creates a new user in the system")
    public ResponseEntity<String> createUser(@RequestBody UserRegisterDto userDto) {
        try {
            String userId = userService.createUser(userDto);
            return ResponseEntity.ok("User created successfully with Username: " + userId);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while creating the user.");
        }
    }

    @PostMapping("/login")
    @Operation(summary = "User login", description = "authenticate user and retrieve tokens")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequestDto loginDto) {
        try {
            return ResponseEntity.ok(userService.loginUser(loginDto));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred during login.");
        }
    }
}