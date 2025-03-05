package com.henrique.pablo.BoardWise.infrastructure.persistence.repository.comment;

import com.henrique.pablo.BoardWise.domain.model.CommentModel;
import com.henrique.pablo.BoardWise.domain.repository.ICommentRepository;
import com.henrique.pablo.BoardWise.infrastructure.persistence.converter.CommentConverter;
import com.henrique.pablo.BoardWise.infrastructure.persistence.entity.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

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
}
