package com.henrique.pablo.BoardWise.application.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record UserUpdateRequest(
        @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
        String username,
        @Size(min = 8, message = "Password must be at least 8 characters")
        String password,
        @Email(message = "Email is invalid")
        String email
) {
}
