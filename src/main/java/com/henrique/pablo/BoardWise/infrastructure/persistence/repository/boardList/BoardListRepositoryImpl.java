package com.henrique.pablo.BoardWise.infrastructure.persistence.repository.boardList;

import com.henrique.pablo.BoardWise.domain.model.BoardListModel;
import com.henrique.pablo.BoardWise.domain.repository.IBoardListRepository;
import com.henrique.pablo.BoardWise.infrastructure.persistence.converter.BoardListConverter;
import com.henrique.pablo.BoardWise.infrastructure.persistence.entity.BoardList;
import com.henrique.pablo.BoardWise.infrastructure.persistence.entity.Project;
import com.henrique.pablo.BoardWise.infrastructure.persistence.repository.project.ProjectJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BoardListRepositoryImpl implements IBoardListRepository {

    private final ProjectJpaRepository projectJpaRepository;
    private final BoardListJpaRepository boardListJpaRepository;

    @Override
    public BoardListModel save(String projectId, BoardListModel boardListModel) {
        BoardList boardList = BoardListConverter.toEntity(boardListModel);

        boardList.setProject(Project.builder().id(projectId).build());

        boardList = boardListJpaRepository.save(boardList);

        return BoardListConverter.toDomain(boardList);
    }

    @Override
    public List<BoardListModel> listBoardLists(String projectId) {
        List<BoardList> boardLists = boardListJpaRepository.findAllByProjectId(projectId);

        return boardLists.stream().map(BoardListConverter::toDomain).toList();
    }
}
