package com.henrique.pablo.BoardWise.application.dto.user;

import java.time.LocalDateTime;

public record UserResponse(
        String id,
        String username,
        String email,
        LocalDateTime createdAt
) {
}
