package com.henrique.pablo.BoardWise.domain.repository;

import com.henrique.pablo.BoardWise.domain.model.CommentModel;

public interface ICommentRepository {
    CommentModel save(CommentModel commentModel);
}
