package com.henrique.pablo.BoardWise.infrastructure.controller;

import com.henrique.pablo.BoardWise.application.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cards")
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;

    //Cria um novo cartão na lista.
    //Retorna detalhes de um cartão.
    //Atualiza um cartão.
    //Deleta um cartão.
    //Move um cartão para outra lista.
}
