package com.henrique.pablo.BoardWise.infrastructure.persistence.repository.card;

import com.henrique.pablo.BoardWise.domain.model.BoardListModel;
import com.henrique.pablo.BoardWise.domain.model.CardModel;
import com.henrique.pablo.BoardWise.domain.repository.ICardRepository;
import com.henrique.pablo.BoardWise.infrastructure.persistence.converter.CardConverter;
import com.henrique.pablo.BoardWise.infrastructure.persistence.entity.BoardList;
import com.henrique.pablo.BoardWise.infrastructure.persistence.entity.Card;
import com.henrique.pablo.BoardWise.infrastructure.persistence.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CardRepositoryImpl implements ICardRepository {

    private final CardJpaRepository cardJpaRepository;

    @Override
    public CardModel save(CardModel cardModel, BoardListModel boardList, Optional<String> assigneeId) {
        Card card = CardConverter.toEntity(cardModel);

        card.setBoardList(BoardList.builder().id(boardList.getId()).build());

        assigneeId.ifPresent(id -> card.setAssignee(User.builder().id(id).build()));

        Card cardSaved = cardJpaRepository.save(card);

        return CardConverter.toDomain(cardSaved);
    }

    @Override
    public Optional<CardModel> findById(String cardId) {
        return cardJpaRepository.findById(cardId)
                .map(CardConverter::toDomain);
    }

    @Override
    public CardModel update(CardModel cardModel, BoardListModel boardList){
        Card card = CardConverter.toEntity(cardModel);

        BoardList boardListEntity = BoardList.builder().id(boardList.getId()).build();

        card.setBoardList(boardListEntity);

        Card cardSaved = cardJpaRepository.save(card);

        return CardConverter.toDomain(cardSaved);
    }

    @Override
    public void delete(String id) {
        cardJpaRepository.deleteById(id);
    }
}
