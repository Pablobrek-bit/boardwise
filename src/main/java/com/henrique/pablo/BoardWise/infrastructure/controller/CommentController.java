package com.henrique.pablo.BoardWise.infrastructure.controller;

import com.henrique.pablo.BoardWise.application.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // Adiciona um comentário a um card
    // Lista todos os comentários de um card
    // Exclui um comentário de um card


}
