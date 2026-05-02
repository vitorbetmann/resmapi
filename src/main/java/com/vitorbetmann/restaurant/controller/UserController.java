package com.vitorbetmann.restaurant.controller;

import com.vitorbetmann.restaurant.dto.*;
import com.vitorbetmann.restaurant.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody CreateUserRequest request) {
        return new ResponseEntity<>(this.userService.createUser(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@Valid @PathVariable Long id, @RequestBody UpdateUserRequest request) {
        return new ResponseEntity<>(this.userService.updateUser(id, request), HttpStatus.OK);
    }

    @PatchMapping("/{id}/password")
    public ResponseEntity<UserResponse> changePassword(@Valid @PathVariable Long id, @RequestBody ChangePasswordRequest request) {
        return new ResponseEntity<>(this.userService.changePassword(id, request), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        this.userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> findByName(@RequestParam String name) {
        return new ResponseEntity<>(this.userService.findByName(name), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<Void> validateLogin(@Valid @RequestBody ValidateLoginRequest request) {
        this.userService.validateLogin(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
