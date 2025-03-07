package com.henrique.pablo.BoardWise.infrastructure.persistence.converter;

import com.henrique.pablo.BoardWise.application.dto.boardList.BoardListRequest;
import com.henrique.pablo.BoardWise.application.dto.boardList.BoardListResponse;
import com.henrique.pablo.BoardWise.domain.model.BoardListModel;
import com.henrique.pablo.BoardWise.infrastructure.persistence.entity.BoardList;

public class BoardListConverter {

    public static BoardList toEntity(BoardListModel boardListModel) {
        return BoardList.builder()
                .id(boardListModel.getId())
                .name(boardListModel.getName())
                .position(boardListModel.getPosition())
                .build();
    }

    public static BoardListModel toDomain(BoardList boardList) {
        return BoardListModel.builder()
                .id(boardList.getId())
                .name(boardList.getName())
                .position(boardList.getPosition())
                .build();
    }

    public static BoardListModel toDomainWithProject(BoardList boardList) {
        return BoardListModel.builder()
                .id(boardList.getId())
                .name(boardList.getName())
                .position(boardList.getPosition())
                .project(ProjectConverter.toDomain(boardList.getProject()))
                .build();
    }

    public static BoardListModel requestToDomain(BoardListRequest boardListRequest) {
        return BoardListModel.builder()
                .name(boardListRequest.name())
                .position(boardListRequest.position())
                .build();
    }

    public static BoardListResponse domainToResponse(BoardListModel boardListModel) {
        return new BoardListResponse(boardListModel.getId(), boardListModel.getName(), boardListModel.getPosition());
    }

}
