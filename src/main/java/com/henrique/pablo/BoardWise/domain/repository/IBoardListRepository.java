package com.henrique.pablo.BoardWise.domain.repository;


import com.henrique.pablo.BoardWise.domain.model.BoardListModel;

public interface IBoardListRepository {

    BoardListModel save(String projectId, BoardListModel boardListModel);

}
