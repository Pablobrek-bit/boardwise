package com.henrique.pablo.BoardWise.infrastructure.persistence.repository.project;

import com.henrique.pablo.BoardWise.domain.model.ProjectModel;
import com.henrique.pablo.BoardWise.domain.model.UserModel;
import com.henrique.pablo.BoardWise.domain.repository.IProjectRepository;
import com.henrique.pablo.BoardWise.infrastructure.persistence.converter.ProjectConverter;
import com.henrique.pablo.BoardWise.infrastructure.persistence.converter.UserConverter;
import com.henrique.pablo.BoardWise.infrastructure.persistence.entity.Project;
import com.henrique.pablo.BoardWise.infrastructure.persistence.entity.User;
import com.henrique.pablo.BoardWise.infrastructure.persistence.repository.user.UserJpaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ProjectRepositoryImpl implements IProjectRepository {

    private final ProjectJpaRepository projectJpaRepository;
    private final UserJpaRepository userJpaRepository;

    @Override
    @Transactional
    public ProjectModel save(String ownerId, ProjectModel projectModel) {
        Project project = ProjectConverter.toEntityWithoutParticipants(projectModel);
        project.setOwner(User.builder().id(ownerId).build());

        project = projectJpaRepository.save(project);

        return ProjectConverter.toDomain(project);
    }

    @Transactional
    public ProjectModel update(ProjectModel projectModel){
        Project project = ProjectConverter.toEntity(projectModel);

        project = projectJpaRepository.save(project);

        return ProjectConverter.toDomain(project);
    }

    @Override
    public Page<ProjectModel> findAll(String ownerId,Pageable pageable, String search) {
        return projectJpaRepository.findByOwnerIdAndSearchTerm(ownerId, search, pageable)
                .map(ProjectConverter::toDomainWithoutParticipants);
    }

    @Override
    public Optional<ProjectModel> findById(String id) {
        return projectJpaRepository.findById(id)
                .map(ProjectConverter::toDomainWithoutParticipants);
    }

    @Override
    public Optional<ProjectModel> findByIdWithParticipants(String id){
        return projectJpaRepository.findByIdWithParticipants(id)
                .map(ProjectConverter::toDomain);
    }

    @Override
    @Transactional
    public ProjectModel addParticipant(String projectId, String participantId) {
        Project project = projectJpaRepository.findByIdWithParticipants(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        User user = userJpaRepository.getReferenceById(participantId);

        project.addParticipant(user);

        project = projectJpaRepository.save(project);
        return ProjectConverter.toDomain(project);
    }

    @Override
    public ProjectModel removeParticipant(String projectId, String participantId) {
        Project project = projectJpaRepository.findByIdWithParticipants(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        User user = userJpaRepository.getReferenceById(participantId);

        project.removeParticipant(user);

        project = projectJpaRepository.save(project);
        return ProjectConverter.toDomain(project);
    }

    @Override
    public Page<UserModel> listParticipants(String projectId, Pageable pageable) {
        Optional<Project> project = projectJpaRepository.findByIdWithParticipantsOnly(projectId);

        if(project.isEmpty()){
            throw new RuntimeException("Project not found");
        }

        List<UserModel> participants = project.get().getParticipants().stream()
                .map(UserConverter::toDomain)
                .toList();

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), participants.size());
        List<UserModel> pagedUserModels = participants.subList(start, end);

        return new PageImpl<>(pagedUserModels, pageable, participants.size());
    }
}
