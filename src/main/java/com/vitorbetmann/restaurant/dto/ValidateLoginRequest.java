package com.vitorbetmann.restaurant.dto;

import jakarta.validation.constraints.NotBlank;

public record ValidateLoginRequest(
        @NotBlank String login,
        @NotBlank String password
) {
}
