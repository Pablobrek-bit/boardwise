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
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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

        CommentModel commentSaved = commentRepository.save(commentModel);

        return CommentConverter.modelToResponse(commentSaved);
    }

    public List<CommentResponse> listComments(String cardId) {
        cardRepository.findById(cardId).orElseThrow(() -> new RuntimeException("Card not found"));

        List<CommentModel> comments = commentRepository.findByCardId(cardId);

        return comments.stream()
                .map(CommentConverter::modelToResponse)
                .toList();
    }

    @Transactional
    public void deleteComment(String cardId, String commentId, String userId) {
        CardModel cardModel = cardRepository.findById(cardId).orElseThrow(() -> new RuntimeException("Card not found"));

        CommentModel commentModel = commentRepository.findById(commentId).orElseThrow(() -> new RuntimeException("Comment not found"));

        if (!cardModel.getAssignee().getId().equals(userId) && !commentModel.getUser().getId().equals(userId)) {
            throw new RuntimeException("User not allowed to delete this comment");
        }

        commentRepository.deleteById(commentId);
    }
}
