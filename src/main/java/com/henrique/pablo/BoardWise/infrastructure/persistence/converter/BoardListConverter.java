package com.henrique.pablo.BoardWise.infrastructure.persistence.converter;

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

}
