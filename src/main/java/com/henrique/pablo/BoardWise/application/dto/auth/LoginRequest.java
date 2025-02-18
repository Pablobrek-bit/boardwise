package com.henrique.pablo.BoardWise.application.dto.auth;

public record LoginRequest(
        String email,
        String password
) {
}
