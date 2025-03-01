package com.henrique.pablo.BoardWise.application.service;

import com.henrique.pablo.BoardWise.application.dto.boardList.BoardListRequest;
import com.henrique.pablo.BoardWise.application.dto.boardList.BoardListResponse;
import com.henrique.pablo.BoardWise.domain.model.BoardListModel;
import com.henrique.pablo.BoardWise.domain.model.ProjectModel;
import com.henrique.pablo.BoardWise.domain.repository.IBoardListRepository;
import com.henrique.pablo.BoardWise.domain.repository.IProjectRepository;
import com.henrique.pablo.BoardWise.shared.exception.ProjectNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardListService {

    private final IBoardListRepository boardListRepository;
    private final IProjectRepository projectRepository;

    @Transactional
    public BoardListResponse create(String projectId, BoardListRequest boardListRequest, String userId) {
        Optional<ProjectModel> project = projectRepository.findById(projectId);

        if (project.isEmpty()) {
            throw new RuntimeException("Project not found");
        }

        if(!project.get().getOwner().getId().equals(userId)) {
            throw new RuntimeException("User does not have permission to create a list in this project");
        }

        BoardListModel boardListModel = BoardListModel.builder()
                .name(boardListRequest.name())
                .position(boardListRequest.position())
                .build();

        BoardListModel boardListSaved = boardListRepository.save(projectId, boardListModel);

        return new BoardListResponse(boardListSaved.getId(), boardListSaved.getName(), boardListSaved.getPosition());
    }

    public List<BoardListResponse> list(String projectId, String userId) {
        // verificar se o projeto existe e depois verificar se o usuario está dentro do project
        var project = projectRepository.findByIdWithParticipants(projectId);

        if (project.isEmpty()) {
            throw new ProjectNotFoundException("Project not found");
        }

        if(!project.get().getOwner().getId().equals(userId) && project.get().getParticipants().stream().noneMatch(p -> p.getId().equals(userId))) {
            throw new RuntimeException("User does not have permission to list the lists in this project");
        }

        List<BoardListModel> boardLists = boardListRepository.listBoardLists(projectId);

        return boardLists.stream().map(boardListModel -> new BoardListResponse(boardListModel.getId(), boardListModel.getName(), boardListModel.getPosition())).toList();
    }
}
