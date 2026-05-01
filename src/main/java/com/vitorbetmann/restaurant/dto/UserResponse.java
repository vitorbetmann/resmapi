package com.vitorbetmann.restaurant.dto;

import java.time.LocalDateTime;

public record UserResponse(
        Long id,
        String name,
        String email,
        String login,
        String address,
        String type,
        LocalDateTime lastModifiedDate) {
}
