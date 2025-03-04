package com.henrique.pablo.BoardWise.application.dto.card;

public record CardRequestUpdate(
        String title,
        String description,
        String priority,
        String status
) {
}
