package com.vitorbetmann.restaurant.dto;

public record ChangeUserPasswordRequest(
        String oldPassword,
        String newPassword) {
}
