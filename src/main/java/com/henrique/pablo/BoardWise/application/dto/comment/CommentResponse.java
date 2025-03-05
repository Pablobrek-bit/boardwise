package com.henrique.pablo.BoardWise.application.dto.comment;

import java.time.LocalDateTime;

public record CommentResponse(
        Integer id,
        String content,
        LocalDateTime createdAt,
        String userId,
        String cardId
) {
}
