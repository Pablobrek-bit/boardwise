package com.henrique.pablo.BoardWise.infrastructure.persistence.repository.comment;

import com.henrique.pablo.BoardWise.infrastructure.persistence.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentJpaRepository extends JpaRepository<Comment, String> {

    @Query("SELECT c FROM Comment c WHERE c.card.id = :cardId")
    List<Comment> findByCardId(String cardId);

    @Query("SELECT c FROM Comment c JOIN FETCH c.user WHERE c.id = :commentId")
    Optional<Comment> findByIdWithUser(String commentId);
}
