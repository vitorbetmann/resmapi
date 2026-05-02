package com.vitorbetmann.restaurant.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UpdateUserRequest(
        @NotBlank String name,
        @NotBlank @Email String email,
        @NotBlank String login,
        @NotBlank String address) {
}
