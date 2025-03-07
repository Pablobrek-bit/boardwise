package com.henrique.pablo.BoardWise.application.service;

import com.henrique.pablo.BoardWise.application.dto.card.CardRequest;
import com.henrique.pablo.BoardWise.application.dto.card.CardRequestUpdate;
import com.henrique.pablo.BoardWise.application.dto.card.CardResponse;
import com.henrique.pablo.BoardWise.domain.model.BoardListModel;
import com.henrique.pablo.BoardWise.domain.model.CardModel;
import com.henrique.pablo.BoardWise.domain.model.ProjectModel;
import com.henrique.pablo.BoardWise.domain.model.UserModel;
import com.henrique.pablo.BoardWise.domain.repository.IBoardListRepository;
import com.henrique.pablo.BoardWise.domain.repository.ICardRepository;
import com.henrique.pablo.BoardWise.domain.repository.IProjectRepository;
import com.henrique.pablo.BoardWise.infrastructure.persistence.converter.CardConverter;
import com.henrique.pablo.BoardWise.shared.exception.IdNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CardService {

    private final ICardRepository cardRepository;
    private final IBoardListRepository boardListRepository;
    private final IProjectRepository projectRepository;

    @Transactional
    public CardResponse create(Integer listId, CardRequest cardRequest, String userId) {
        BoardListModel boardList = verifyIfUserBelongTheProject(listId, userId);

        CardModel cardModel = CardConverter.requestToDomain(cardRequest);

        Optional<String> assigneeId = Optional.ofNullable(cardRequest.assigneeId());

        CardModel cardModelSaved = cardRepository.save(cardModel, boardList, assigneeId);

        return CardConverter.toResponse(cardModelSaved);
    }

    public CardResponse getCard(String cardId, Integer listId, String userId) {
        verifyIfUserBelongTheProject(listId, userId);

        CardModel cardModel = cardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("Card not found"));

        return CardConverter.toResponse(cardModel);
    }

    @Transactional
    public CardResponse update(Integer listId, String cardId, CardRequestUpdate cardRequest, String userId) {
        BoardListModel boardListModel = verifyIfUserBelongTheProject(listId, userId);

        CardModel cardModel = cardRepository.findById(cardId)
                .orElseThrow(() -> new IdNotFoundException("Card not found"));

        if(cardRequest.title() != null){
            cardModel.setTitle(cardRequest.title());
        }

        if(cardRequest.description() != null){
            cardModel.setDescription(cardRequest.description());
        }

        if(cardRequest.priority() != null){
            cardModel.setPriority(cardRequest.priority());
        }

        if(cardRequest.status() != null){
            cardModel.setStatus(cardRequest.status());
        }

        CardModel cardModelUpdated = cardRepository.update(cardModel, boardListModel);

        return CardConverter.toResponse(cardModelUpdated);

    }

    @Transactional
    public void delete(Integer listId, String cardId, String userId) {
        verifyIfUserBelongTheProject(listId, userId);

        cardRepository.findById(cardId)
                .ifPresent(cardModel -> cardRepository.delete(cardModel.getId()));
    }

    @Transactional
    public void assignUserToCard(Integer listId, String cardId, String userId, String requesterId) {
        BoardListModel boardListModel = verifyIfUserBelongTheProject(listId, requesterId);

        CardModel cardModel = cardRepository.findById(cardId)
                .orElseThrow(() -> new IdNotFoundException("Card not found"));

        if (cardModel.getAssignee() != null) {
            throw new RuntimeException("Card already has an assignee");
        }

        cardModel.setAssignee(UserModel.builder().id(userId).build());

        cardRepository.update(cardModel, boardListModel);
    }

    @Transactional
    public void removeUserFromCard(Integer listId, String cardId, String userId, String requesterId) {
        BoardListModel boardListModel = verifyIfUserBelongTheProject(listId, requesterId);

        CardModel cardModel = cardRepository.findById(cardId)
                .orElseThrow(() -> new IdNotFoundException("Card not found"));

        if (cardModel.getAssignee() == null || !cardModel.getAssignee().getId().equals(userId)) {
            throw new RuntimeException("User is not the assignee of the card");
        }

        cardModel.setAssignee(null);

        cardRepository.update(cardModel, boardListModel);
    }

    @Transactional
    public void moveCard(Integer listId, String cardId, Integer targetListId, String userId) {
        verifyIfUserBelongTheProject(listId, userId);

        BoardListModel targetBoardListModel = verifyIfUserBelongTheProject(targetListId, userId);

        CardModel cardModel = cardRepository.findById(cardId)
                .orElseThrow(() -> new IdNotFoundException("Card not found"));

        cardRepository.update(cardModel, targetBoardListModel);
    }

    private BoardListModel verifyIfUserBelongTheProject(Integer listId, String userId) {
        BoardListModel boardList = boardListRepository.findByIdWithProject(listId);

        ProjectModel project = projectRepository.findByIdWithParticipants(boardList.getProject().getId())
                .orElseThrow(() -> new IdNotFoundException("Project not found"));

        if (project.getParticipants().stream().noneMatch(member -> member.getId().equals(userId)) && !project.getOwner().getId().equals(userId)) {
            throw new RuntimeException("User is not a member of the project");
        }

        return boardList;
    }
}
