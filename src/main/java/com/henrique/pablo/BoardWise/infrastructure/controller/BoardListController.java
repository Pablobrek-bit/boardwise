package com.henrique.pablo.BoardWise.infrastructure.controller;

import com.henrique.pablo.BoardWise.application.dto.boardList.BoardListRequest;
import com.henrique.pablo.BoardWise.application.dto.boardList.BoardListRequestUpdate;
import com.henrique.pablo.BoardWise.application.dto.boardList.BoardListResponse;
import com.henrique.pablo.BoardWise.application.service.BoardListService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/board-list")
@RequiredArgsConstructor
public class BoardListController {

    private final BoardListService boardListService;

    @PostMapping("/{projectId}")
    public BoardListResponse createBoardList(@PathVariable String projectId,
                                             @Valid @RequestBody BoardListRequest boardListRequest,
                                             @RequestAttribute("id") String userId
    ) {
        return boardListService.create(projectId, boardListRequest, userId);
    }

    @GetMapping("/{projectId}")
    public List<BoardListResponse> listBoardListByProject(@PathVariable String projectId,
                                                 @RequestAttribute("id") String userId){
        return boardListService.list(projectId, userId);
    }

    @PutMapping("/{projectId}/{boardListId}")
    public BoardListResponse updateBoardList(@PathVariable String projectId,
                                             @PathVariable Integer boardListId,
                                             @Valid @RequestBody BoardListRequestUpdate boardListRequestUpdate,
                                             @RequestAttribute("id") String userId
    ) {
        return boardListService.update(projectId, boardListId, boardListRequestUpdate, userId);
    }

    @DeleteMapping("/{projectId}/{boardListId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBoardList(@PathVariable String projectId,
                                @PathVariable Integer boardListId,
                                @RequestAttribute("id") String userId
    ) {
        boardListService.delete(projectId, boardListId, userId);
    }

}
