package com.vitorbetmann.restaurant.dto;

public record ValidateLoginRequest(
        String login,
        String password
) {
}
