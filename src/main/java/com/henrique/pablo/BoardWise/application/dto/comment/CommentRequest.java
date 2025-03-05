package com.henrique.pablo.BoardWise.application.dto.comment;

import jakarta.validation.constraints.NotBlank;

public record CommentRequest(
        @NotBlank(message = "Content is required")
        String content
) {
}
