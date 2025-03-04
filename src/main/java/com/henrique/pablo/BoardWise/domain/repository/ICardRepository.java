package com.henrique.pablo.BoardWise.domain.repository;

import com.henrique.pablo.BoardWise.domain.model.BoardListModel;
import com.henrique.pablo.BoardWise.domain.model.CardModel;

import java.util.Optional;

public interface ICardRepository {
    CardModel save(CardModel cardModel, BoardListModel boardList, Optional<String> assigneeId);

    Optional<CardModel> findById(String cardId);

    CardModel update(CardModel cardModel, BoardListModel boardListModel);
}
