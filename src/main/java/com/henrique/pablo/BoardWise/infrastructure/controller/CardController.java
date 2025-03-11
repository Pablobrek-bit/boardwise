package com.henrique.pablo.BoardWise.infrastructure.controller;

import com.henrique.pablo.BoardWise.application.dto.card.CardRequest;
import com.henrique.pablo.BoardWise.application.dto.card.CardRequestUpdate;
import com.henrique.pablo.BoardWise.application.dto.card.CardResponse;
import com.henrique.pablo.BoardWise.application.service.CardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cards")
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;

    @PostMapping("/lists/{listId}/cards")
    public CardResponse createCard(@PathVariable Integer listId,
                                   @Valid @RequestBody CardRequest cardRequest,
                                   @RequestAttribute("id") String userId
    ) {
        return cardService.create(listId, cardRequest, userId);
    }

    @GetMapping("/lists/{listId}/cards/{cardId}")
    public CardResponse getCardByList(@PathVariable Integer listId,
                                @PathVariable String cardId,
                                @RequestAttribute("id") String userId){
        return cardService.getCard(cardId, listId, userId);
    }

    @PutMapping("/lists/{listId}/cards/{cardId}")
    public CardResponse updateCard(@PathVariable Integer listId,
                                   @PathVariable String cardId,
                                   @Valid @RequestBody CardRequestUpdate cardRequest,
                                   @RequestAttribute("id") String userId
    ) {
        return cardService.update(listId, cardId, cardRequest, userId);
    }

    @DeleteMapping("/lists/{listId}/cards/{cardId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCard(@PathVariable Integer listId,
                           @PathVariable String cardId,
                           @RequestAttribute("id") String userId
    ) {
        cardService.delete(listId, cardId, userId);
    }

    @PutMapping("/lists/{listId}/cards/{cardId}/assignee")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void assignUserToCard(@PathVariable Integer listId,
                                 @PathVariable String cardId,
                                 @RequestParam String userId,
                                 @RequestAttribute("id") String requesterId
    ) {
        cardService.assignUserToCard(listId, cardId, userId, requesterId);
    }

    @DeleteMapping("/lists/{listId}/cards/{cardId}/assignee")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeUserFromCard(@PathVariable Integer listId,
                                   @PathVariable String cardId,
                                   @RequestParam String userId,
                                   @RequestAttribute("id") String requesterId
    ) {
        cardService.removeUserFromCard(listId, cardId, userId, requesterId);
    }

    @PutMapping("/lists/{listId}/cards/{cardId}/move")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void moveCardToAnotherList(@PathVariable Integer listId,
                        @PathVariable String cardId,
                        @RequestParam Integer targetListId,
                        @RequestAttribute("id") String userId
    ) {
        cardService.moveCard(listId, cardId, targetListId, userId);
    }
}
