package com.henrique.pablo.BoardWise.application.service;

import com.henrique.pablo.BoardWise.domain.repository.ICommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final ICommentRepository commentRepository;

}
