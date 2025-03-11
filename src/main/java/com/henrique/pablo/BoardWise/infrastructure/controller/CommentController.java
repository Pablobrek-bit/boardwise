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

    @PostMapping("/cards/{cardId}")
    public CommentResponse createCommentInTheCard(@RequestAttribute("id") String userId,
                                         @Valid @RequestBody CommentRequest commentRequest,
                                         @PathVariable String cardId ){
        return commentService.createComment(userId, commentRequest, cardId);
    }

    @GetMapping("/cards/{cardId}")
    public List<CommentResponse> listCommentsByCard(@PathVariable String cardId){
        return commentService.listComments(cardId);
    }

    @DeleteMapping("/cards/{cardId}/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCommentFromCard(@PathVariable String cardId,
                              @PathVariable String commentId,
                              @RequestAttribute("id") String userId){
        commentService.deleteComment(cardId, commentId, userId);
    }

}
