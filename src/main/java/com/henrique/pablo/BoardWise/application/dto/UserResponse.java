package com.henrique.pablo.BoardWise.application.dto;

import java.time.LocalDateTime;

public record UserResponse(
        String id,
        String username,
        String email,
        LocalDateTime createdAt
) {
}
