package com.vitorbetmann.resmapi.dto;

import jakarta.validation.constraints.NotBlank;

public record ValidateLoginRequest(
        @NotBlank String login,
        @NotBlank String password
) {
}
