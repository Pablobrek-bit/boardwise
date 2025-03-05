package com.henrique.pablo.BoardWise.infrastructure.controller;

import com.henrique.pablo.BoardWise.application.dto.comment.CommentRequest;
import com.henrique.pablo.BoardWise.application.dto.comment.CommentResponse;
import com.henrique.pablo.BoardWise.application.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // Adiciona um comentário a um card
    @PostMapping("/cards/{cardId}")
    public CommentResponse createComment(@RequestAttribute("id") String userId,
                                         @Valid @RequestBody CommentRequest commentRequest,
                                         @PathVariable String cardId ){
        return commentService.createComment(userId, commentRequest, cardId);
    }

    // Lista todos os comentários de um card
    // Exclui um comentário de um card

}
