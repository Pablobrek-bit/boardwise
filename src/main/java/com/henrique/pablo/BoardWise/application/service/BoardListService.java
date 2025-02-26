package com.henrique.pablo.BoardWise.application.service;

import com.henrique.pablo.BoardWise.domain.repository.IBoardListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardListService {
    private IBoardListRepository boardListRepository;

}
