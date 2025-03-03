package com.henrique.pablo.BoardWise.application.service;

import com.henrique.pablo.BoardWise.domain.repository.ICardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CardService {

    private final ICardRepository cardRepository;

}
