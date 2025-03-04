package com.henrique.pablo.BoardWise.application.dto.card;

import java.time.LocalDateTime;

public record CardResponse(
        String id,
        String title,
        String description,
        LocalDateTime dueDate,
        String priority,
        String status,
        Integer boardListId,
        String assigneeId
) {
}
