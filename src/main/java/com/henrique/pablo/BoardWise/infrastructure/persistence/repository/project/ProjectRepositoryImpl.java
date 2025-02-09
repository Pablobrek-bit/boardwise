package com.henrique.pablo.BoardWise.infrastructure.persistence.repository.project;

import com.henrique.pablo.BoardWise.domain.model.ProjectModel;
import com.henrique.pablo.BoardWise.domain.repository.IProjectRepository;
import com.henrique.pablo.BoardWise.infrastructure.persistence.converter.ProjectConverter;
import com.henrique.pablo.BoardWise.infrastructure.persistence.entity.Project;
import com.henrique.pablo.BoardWise.infrastructure.persistence.entity.User;
import com.henrique.pablo.BoardWise.infrastructure.persistence.repository.user.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProjectRepositoryImpl implements IProjectRepository {

    private final ProjectJpaRepository projectJpaRepository;

    @Override
    public ProjectModel save(User user, ProjectModel projectModel) {
        Project project = ProjectConverter.toEntityWithoutOwner(projectModel);
        project.setOwner(user);
        project = projectJpaRepository.save(project);

        return ProjectConverter.toDomain(project);
    }

    @Override
    public Page<ProjectModel> findAll(Pageable pageable) {
        return projectJpaRepository.findAll(pageable)
                .map(ProjectConverter::toDomain);
    }
}
