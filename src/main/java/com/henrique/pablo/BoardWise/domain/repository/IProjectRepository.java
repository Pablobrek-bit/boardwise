package com.henrique.pablo.BoardWise.domain.repository;

import com.henrique.pablo.BoardWise.application.dto.ProjectRequest;
import com.henrique.pablo.BoardWise.domain.model.ProjectModel;
import com.henrique.pablo.BoardWise.infrastructure.persistence.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IProjectRepository {
    ProjectModel save(User user, ProjectModel projectModel);
    Page<ProjectModel> findAll(Pageable pageable);
}
