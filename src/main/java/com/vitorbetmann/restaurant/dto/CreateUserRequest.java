package com.vitorbetmann.restaurant.dto;

public record CreateUserRequest(
        String name,
        String email,
        String login,
        String password,
        String address,
        String type) {
}
