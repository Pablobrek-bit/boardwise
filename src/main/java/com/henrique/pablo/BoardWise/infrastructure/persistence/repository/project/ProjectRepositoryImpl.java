package com.henrique.pablo.BoardWise.infrastructure.persistence.repository.project;

import com.henrique.pablo.BoardWise.domain.model.ProjectModel;
import com.henrique.pablo.BoardWise.domain.repository.IProjectRepository;
import com.henrique.pablo.BoardWise.infrastructure.persistence.converter.ProjectConverter;
import com.henrique.pablo.BoardWise.infrastructure.persistence.entity.Project;
import com.henrique.pablo.BoardWise.infrastructure.persistence.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ProjectRepositoryImpl implements IProjectRepository {

    private final ProjectJpaRepository projectJpaRepository;

    @Override
    public ProjectModel save(String ownerId, ProjectModel projectModel) {
        Project project = ProjectConverter.toEntityWithoutOwner(projectModel);
        project.setOwner(User.builder().id(ownerId).build());
        project = projectJpaRepository.save(project);

        return ProjectConverter.toDomain(project);
    }

    @Override
    public Page<ProjectModel> findAll( String ownerId,Pageable pageable, String search) {
        return projectJpaRepository.findByOwnerIdAndSearchTerm(ownerId, search, pageable)
                .map(ProjectConverter::toDomain);
    }

    @Override
    public Optional<ProjectModel> findById(String id) {
        return projectJpaRepository.findById(id)
                .map(ProjectConverter::toDomain);
    }
}
