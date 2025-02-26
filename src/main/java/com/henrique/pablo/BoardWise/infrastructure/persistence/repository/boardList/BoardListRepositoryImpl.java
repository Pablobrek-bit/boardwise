package com.henrique.pablo.BoardWise.infrastructure.persistence.repository.boardList;

import com.henrique.pablo.BoardWise.domain.repository.IBoardListRepository;
import com.henrique.pablo.BoardWise.infrastructure.persistence.repository.project.ProjectJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BoardListRepositoryImpl implements IBoardListRepository {

    private final ProjectJpaRepository projectJpaRepository;

}
