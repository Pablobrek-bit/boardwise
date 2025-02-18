package com.henrique.pablo.BoardWise.application.dto.project;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ProjectRequest(
        @NotBlank(message = "Name is required")
        @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
        String name,

        @Size(max = 255, message = "Description must be less than 255 characters")
        String description
        ) {
}
