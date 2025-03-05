package com.henrique.pablo.BoardWise.application.service;

import com.henrique.pablo.BoardWise.application.dto.comment.CommentRequest;
import com.henrique.pablo.BoardWise.application.dto.comment.CommentResponse;
import com.henrique.pablo.BoardWise.domain.model.CardModel;
import com.henrique.pablo.BoardWise.domain.model.CommentModel;
import com.henrique.pablo.BoardWise.domain.model.UserModel;
import com.henrique.pablo.BoardWise.domain.repository.ICardRepository;
import com.henrique.pablo.BoardWise.domain.repository.ICommentRepository;
import com.henrique.pablo.BoardWise.domain.repository.IUserRepository;
import com.henrique.pablo.BoardWise.infrastructure.persistence.converter.CommentConverter;
import com.henrique.pablo.BoardWise.infrastructure.persistence.entity.Card;
import com.henrique.pablo.BoardWise.infrastructure.persistence.entity.Comment;
import com.henrique.pablo.BoardWise.infrastructure.persistence.entity.User;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final ICommentRepository commentRepository;
    private final IUserRepository userRepository;
    private final ICardRepository cardRepository;

    @Transactional
    public CommentResponse createComment(String userId, CommentRequest commentRequest, String cardId) {
        userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        cardRepository.findById(cardId).orElseThrow(() -> new RuntimeException("Card not found"));

        CommentModel commentModel = CommentConverter.requestToModel(commentRequest);

        commentModel.setUser(UserModel.builder().id(userId).build());
        commentModel.setCard(CardModel.builder().id(cardId).build());

        System.out.println("chegou aqui 1");

        CommentModel commentSaved = commentRepository.save(commentModel);

        return CommentConverter.modelToResponse(commentSaved);
    }
}
