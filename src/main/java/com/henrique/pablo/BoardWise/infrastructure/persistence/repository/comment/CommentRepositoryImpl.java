package com.henrique.pablo.BoardWise.infrastructure.persistence.repository.comment;

import com.henrique.pablo.BoardWise.domain.model.CommentModel;
import com.henrique.pablo.BoardWise.domain.repository.ICommentRepository;
import com.henrique.pablo.BoardWise.infrastructure.persistence.converter.CommentConverter;
import com.henrique.pablo.BoardWise.infrastructure.persistence.entity.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryImpl implements ICommentRepository {

    private final CommentJpaRepository commentJpaRepository;

    @Override
    public CommentModel save(CommentModel commentModel) {
        Comment comment = CommentConverter.toEntity(commentModel);

        Comment savedComment = commentJpaRepository.save(comment);

        return CommentConverter.toDomain(savedComment);
    }

    @Override
    public List<CommentModel> findByCardId(String cardId) {
        List<Comment> comments = commentJpaRepository.findByCardId(cardId);

        return comments.stream()
                .map(CommentConverter::toDomain)
                .toList();
    }

    @Override
    public Optional<CommentModel> findById(String commentId) {
        Optional<Comment> comment = commentJpaRepository.findById(commentId);

        return comment.map(CommentConverter::toDomain);
    }

    @Override
    public void deleteById(String commentId) {
        commentJpaRepository.deleteById(commentId);
    }
}
