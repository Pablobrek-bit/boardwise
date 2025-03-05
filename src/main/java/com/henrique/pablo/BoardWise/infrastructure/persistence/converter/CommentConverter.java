package com.henrique.pablo.BoardWise.infrastructure.persistence.converter;

import com.henrique.pablo.BoardWise.application.dto.comment.CommentRequest;
import com.henrique.pablo.BoardWise.application.dto.comment.CommentResponse;
import com.henrique.pablo.BoardWise.domain.model.CommentModel;
import com.henrique.pablo.BoardWise.infrastructure.persistence.entity.Comment;

public class CommentConverter {

    public static Comment toEntity(CommentModel commentModel){
        return Comment.builder()
                .id(commentModel.getId())
                .content(commentModel.getContent())
                .createdAt(commentModel.getCreatedAt())
                .user(UserConverter.toEntity(commentModel.getUser()))
                .card(CardConverter.toEntity(commentModel.getCard()))
                .build();
    }

    public static CommentModel toDomain(Comment comment){
        return CommentModel.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .user(UserConverter.toDomain(comment.getUser()))
                .card(CardConverter.toDomain(comment.getCard()))
                .build();
    }

    public static CommentModel requestToModel(CommentRequest commentRequest){
        return CommentModel.builder()
                .content(commentRequest.content())
                .build();
    }

    public static CommentResponse modelToResponse(CommentModel commentModel){
        return new CommentResponse(
                commentModel.getId(),
                commentModel.getContent(),
                commentModel.getCreatedAt(),
                commentModel.getUser().getId(),
                commentModel.getCard().getId()
        );
    }

}
