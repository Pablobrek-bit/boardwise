package com.henrique.pablo.BoardWise.application.dto.boardList;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BoardListRequest(

        @NotBlank(message = "Name is required")
        String name,

        @NotNull(message = "Position is required")
        int position
) {}
