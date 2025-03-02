package com.henrique.pablo.BoardWise.domain.repository;


import com.henrique.pablo.BoardWise.domain.model.BoardListModel;

import java.util.List;

public interface IBoardListRepository {
    BoardListModel save(String projectId, BoardListModel boardListModel);
    List<BoardListModel> listBoardLists(String projectId);
    BoardListModel update(BoardListModel boardListModel, String projectId);
    BoardListModel findById(Integer boardListId);
    void delete(Integer boardListId);
}
