package com.henrique.pablo.BoardWise.infrastructure.controller;

import com.henrique.pablo.BoardWise.application.dto.comment.CommentRequest;
import com.henrique.pablo.BoardWise.application.dto.comment.CommentResponse;
import com.henrique.pablo.BoardWise.application.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @GetMapping("/cards/{cardId}")
    public List<CommentResponse> listComments(@PathVariable String cardId){
        return commentService.listComments(cardId);
    }

    // Exclui um comentário de um card
    @DeleteMapping("/cards/{cardId}/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable String cardId,
                              @PathVariable String commentId,
                              @RequestAttribute("id") String userId){
        commentService.deleteComment(cardId, commentId, userId);
    }

}
