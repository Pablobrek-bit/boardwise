package com.henrique.pablo.BoardWise.infrastructure.controller;

import com.henrique.pablo.BoardWise.application.dto.card.CardRequest;
import com.henrique.pablo.BoardWise.application.dto.card.CardRequestUpdate;
import com.henrique.pablo.BoardWise.application.dto.card.CardResponse;
import com.henrique.pablo.BoardWise.application.service.CardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cards")
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;

    //Cria um cartão na lista.
    @PostMapping("/lists/{listId}/cards")
    public CardResponse createCard(@PathVariable Integer listId,
                                   @Valid @RequestBody CardRequest cardRequest,
                                   @RequestAttribute("id") String userId
    ) {
        return cardService.create(listId, cardRequest, userId);
    }

    //Retorna detalhes de um cartão.
    @GetMapping("/lists/{listId}/cards/{cardId}")
    public CardResponse getCard(@PathVariable Integer listId,
                                @PathVariable String cardId,
                                @RequestAttribute("id") String userId){
        return cardService.getCard(cardId, listId, userId);
    }

    //Atualiza um cartão.
    @PutMapping("/lists/{listId}/cards/{cardId}")
    public CardResponse updateCard(@PathVariable Integer listId,
                                   @PathVariable String cardId,
                                   @Valid @RequestBody CardRequestUpdate cardRequest,
                                   @RequestAttribute("id") String userId
    ) {
        return cardService.update(listId, cardId, cardRequest, userId);
    }

    //Excluir um cartão.
    //Move um cartão para outra lista.
}
