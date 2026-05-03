package com.vitorbetmann.resmapi.controller;

import com.vitorbetmann.resmapi.dto.*;
import com.vitorbetmann.resmapi.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    @Operation(
            summary = "Creates a new user.",
            description = "Creates a new user with a unique EMAIL and ID fields.",
            responses = {
                    @ApiResponse(description = "CREATED", responseCode = "201")
            }
    )
    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody CreateUserRequest request) {
        return new ResponseEntity<>(this.userService.createUser(request), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Updates a user's information. Excludes password.",
            description = "Updates at least one of the following fields of a user with a given ID: NAME, EMAIL, LOGIN, ADDRESS.",
            responses = {
                    @ApiResponse(description = "OK", responseCode = "200")
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@Valid @PathVariable Long id, @RequestBody UpdateUserRequest request) {
        return new ResponseEntity<>(this.userService.updateUser(id, request), HttpStatus.OK);
    }

    @Operation(
            summary = "Updates a user's password.",
            description = "Updates the password field of a user with a given ID.",
            responses = {
                    @ApiResponse(description = "OK", responseCode = "200")
            }
    )
    @PatchMapping("/{id}/password")
    public ResponseEntity<UserResponse> changePassword(@Valid @PathVariable Long id, @RequestBody ChangePasswordRequest request) {
        return new ResponseEntity<>(this.userService.changePassword(id, request), HttpStatus.OK);
    }

    @Operation(
            summary = "Deletes a user.",
            description = "Deletes the user with a given ID.",
            responses = {
                    @ApiResponse(description = "NO CONTENT", responseCode = "204")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        this.userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(
            summary = "Finds users by name.",
            description = "Returns a list with all users with a given NAME.",
            responses = {
                    @ApiResponse(description = "OK", responseCode = "200")
            }
    )
    @GetMapping
    public ResponseEntity<List<UserResponse>> findByName(@RequestParam String name) {
        return new ResponseEntity<>(this.userService.findByName(name), HttpStatus.OK);
    }

    @Operation(
            summary = "Validates a user's login attempt.",
            responses = {
                    @ApiResponse(description = "OK", responseCode = "200")
            }
    )
    @PostMapping("/login")
    public ResponseEntity<Void> validateLogin(@Valid @RequestBody ValidateLoginRequest request) {
        this.userService.validateLogin(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
