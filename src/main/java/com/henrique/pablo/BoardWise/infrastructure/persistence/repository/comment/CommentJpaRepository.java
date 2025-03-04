package com.henrique.pablo.BoardWise.infrastructure.persistence.repository.comment;

import com.henrique.pablo.BoardWise.infrastructure.persistence.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentJpaRepository extends JpaRepository<Comment, String> {

}
