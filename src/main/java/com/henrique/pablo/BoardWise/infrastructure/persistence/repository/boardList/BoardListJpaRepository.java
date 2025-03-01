package com.henrique.pablo.BoardWise.infrastructure.persistence.repository.boardList;

import com.henrique.pablo.BoardWise.infrastructure.persistence.entity.BoardList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardListJpaRepository extends JpaRepository<BoardList, Integer> {
    List<BoardList> findAllByProjectId(String projectId);
}
