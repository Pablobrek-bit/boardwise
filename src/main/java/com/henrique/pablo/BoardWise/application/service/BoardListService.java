package com.henrique.pablo.BoardWise.application.service;

import com.henrique.pablo.BoardWise.application.dto.boardList.BoardListRequest;
import com.henrique.pablo.BoardWise.application.dto.boardList.BoardListResponse;
import com.henrique.pablo.BoardWise.domain.model.BoardListModel;
import com.henrique.pablo.BoardWise.domain.repository.IBoardListRepository;
import com.henrique.pablo.BoardWise.domain.repository.IProjectRepository;
import com.henrique.pablo.BoardWise.domain.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardListService {

    private final IBoardListRepository boardListRepository;
    private final IProjectRepository projectRepository;
    private final IUserRepository userRepository;

    public BoardListResponse create(String projectId, BoardListRequest boardListRequest, String userId) {
        var project = projectRepository.findById(projectId);

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

        var boardListSaved = boardListRepository.save(projectId, boardListModel);

        return new BoardListResponse(boardListSaved.getId(), boardListSaved.getName(), boardListSaved.getPosition());
    }
}
