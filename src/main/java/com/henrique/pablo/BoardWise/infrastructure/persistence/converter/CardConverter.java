package com.henrique.pablo.BoardWise.infrastructure.persistence.converter;

import com.henrique.pablo.BoardWise.application.dto.card.CardRequest;
import com.henrique.pablo.BoardWise.application.dto.card.CardResponse;
import com.henrique.pablo.BoardWise.domain.model.CardModel;
import com.henrique.pablo.BoardWise.infrastructure.persistence.entity.Card;

public class CardConverter {

    public static CardModel toDomain(Card card){
        return CardModel.builder()
                .id(card.getId())
                .title(card.getTitle())
                .description(card.getDescription())
                .dueDate(card.getDueDate())
                .priority(card.getPriority())
                .status(card.getStatus())
                .createdAt(card.getCreatedAt())
                .updatedAt(card.getUpdatedAt())
                .boardList(BoardListConverter.toDomain(card.getBoardList()))
                .assignee(card.getAssignee() != null ? UserConverter.toDomain(card.getAssignee()) : null)
                .build();
    }

    public static Card toEntity(CardModel cardModel){
        return Card.builder()
                .id(cardModel.getId())
                .title(cardModel.getTitle())
                .description(cardModel.getDescription())
                .dueDate(cardModel.getDueDate())
                .priority(cardModel.getPriority())
                .status(cardModel.getStatus())
                .createdAt(cardModel.getCreatedAt())
                .updatedAt(cardModel.getUpdatedAt())
//                .boardList(BoardListConverter.toEntity(cardModel.getBoardList()))
                .assignee(cardModel.getAssignee() != null ? UserConverter.toEntity(cardModel.getAssignee()) : null)
                .build();
    }

    public static CardModel toDomainWithBoardList(Card card){
        return CardModel.builder()
                .id(card.getId())
                .title(card.getTitle())
                .description(card.getDescription())
                .dueDate(card.getDueDate())
                .priority(card.getPriority())
                .status(card.getStatus())
                .createdAt(card.getCreatedAt())
                .updatedAt(card.getUpdatedAt())
                .boardList(BoardListConverter.toDomain(card.getBoardList()))
                .assignee(UserConverter.toDomain(card.getAssignee()))
                .build();
    }

    public static CardModel requestToDomain(CardRequest cardRequest){
        return CardModel.builder()
                .title(cardRequest.title())
                .description(cardRequest.description())
                .dueDate(cardRequest.dueDate())
                .priority(cardRequest.priority())
                .status(cardRequest.status())
                .build();
    }

    public static CardResponse toResponse(CardModel cardModel) {
        return new CardResponse(
                cardModel.getId(),
                cardModel.getTitle(),
                cardModel.getDescription(),
                cardModel.getDueDate(),
                cardModel.getPriority(),
                cardModel.getStatus(),
                cardModel.getBoardList().getId(),
                cardModel.getAssignee() != null ? cardModel.getAssignee().getId() : null
        );
    }

}
