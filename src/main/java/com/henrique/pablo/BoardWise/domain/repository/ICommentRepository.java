package com.henrique.pablo.BoardWise.domain.repository;

import com.henrique.pablo.BoardWise.domain.model.CommentModel;

import java.util.List;
import java.util.Optional;

public interface ICommentRepository {
    CommentModel save(CommentModel commentModel);

    List<CommentModel> findByCardId(String cardId);

    Optional<CommentModel> findById(String commentId);

    void deleteById(String commentId);
}
