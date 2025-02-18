package com.henrique.pablo.BoardWise.application.dto.project;

public record ProjectResponse(
        String id,
        String name,
        String description,
        String ownerId
) {
}
