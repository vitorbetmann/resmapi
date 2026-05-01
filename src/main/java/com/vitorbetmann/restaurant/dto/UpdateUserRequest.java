package com.vitorbetmann.restaurant.dto;

public record UpdateUserRequest(
        String name,
        String email,
        String login,
        String address) {
}
