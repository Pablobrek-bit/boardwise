package com.henrique.pablo.BoardWise.application.dto.card;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record CardRequest(

        @NotBlank(message = "Title is mandatory")
        String title,
        String description,

        @NotNull(message = "Due date is mandatory")
        LocalDateTime dueDate,

        @NotBlank(message = "Priority is mandatory")
        String priority,

        @NotBlank(message = "Status is mandatory")
        String status,

        String assigneeId
) {

    //exemplo de body de requisição
    //{
    //    "title": "Criar testes",
    //    "description": "Criar testes para a aplicação",
    //    "dueDate": "2021-10-10T10:00:00",
    //    "priority": "HIGH",
    //    "status": "TODO"
    //
    // }


}
