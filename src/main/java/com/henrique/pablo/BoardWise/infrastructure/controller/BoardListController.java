package com.henrique.pablo.BoardWise.infrastructure.controller;

import com.henrique.pablo.BoardWise.application.dto.boardList.BoardListRequest;
import com.henrique.pablo.BoardWise.application.dto.boardList.BoardListResponse;
import com.henrique.pablo.BoardWise.application.service.BoardListService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/board-list")
@RequiredArgsConstructor
public class BoardListController {

    private final BoardListService boardListService;

    // Cria uma nova lista no projeto. (verificar se o usuário tem permissão para criar uma lista no projeto, verificar se o projeto existe)
    @PostMapping("/{projectId}")
    public BoardListResponse createBoardList(@PathVariable String projectId,
                                             @Valid @RequestBody BoardListRequest boardListRequest,
                                             @RequestAttribute("id") String userId
    ) {
        return boardListService.create(projectId, boardListRequest, userId);
    }


    // Lista todas as listas do projeto.
    @GetMapping("/{projectId}")
    public List<BoardListResponse> listBoardList(@PathVariable String projectId,
                              @RequestAttribute("id") String userId){
        return boardListService.list(projectId, userId);
    }

    // Atualiza uma lista (ex: nome, posição).
    // Exclui uma lista.


}
