package com.vitorbetmann.restaurant.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateUserRequest(
        @NotBlank String name,
        @NotBlank @Email String email,
        @NotBlank String login,
        @NotBlank String password,
        @NotBlank String address,
        @NotBlank String type) {
}
