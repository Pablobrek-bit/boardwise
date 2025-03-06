package com.henrique.pablo.BoardWise.application.service;

import com.henrique.pablo.BoardWise.application.dto.project.ProjectRequest;
import com.henrique.pablo.BoardWise.application.dto.project.ProjectResponse;
import com.henrique.pablo.BoardWise.application.dto.project.ProjectResponseWithParticipants;
import com.henrique.pablo.BoardWise.application.dto.user.UserResponse;
import com.henrique.pablo.BoardWise.domain.model.ProjectModel;
import com.henrique.pablo.BoardWise.domain.model.UserModel;
import com.henrique.pablo.BoardWise.domain.repository.IProjectRepository;
import com.henrique.pablo.BoardWise.infrastructure.persistence.converter.ProjectConverter;
import com.henrique.pablo.BoardWise.infrastructure.persistence.converter.UserConverter;
import com.henrique.pablo.BoardWise.shared.exception.ProjectNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final IProjectRepository projectRepository;

    @Transactional
    public ProjectResponse createProject(String ownerId, @Valid ProjectRequest projectRequest) {
        ProjectModel projectModel = ProjectModel.builder()
                .name(projectRequest.name())
                .description(projectRequest.description())
                .build();

        ProjectModel savedProject = projectRepository.save(ownerId, projectModel);

        return ProjectConverter.modelToResponse(savedProject);
    }

    public Page<ProjectResponse> listProjects(int page, int size, String sort, String direction, String ownerId, String search){
        Pageable pageable = PageRequest.of(page, size,
                Sort.by(direction.equalsIgnoreCase("asc") ? Sort.Direction.ASC :
                        Sort.Direction.DESC, sort));

        Page<ProjectModel> projects = projectRepository.findAll(ownerId,pageable, search);

        return projects.map(ProjectConverter::modelToResponse);
    }

    public ProjectResponse findProjectById(String id, String ownerId) {
        ProjectModel project = projectRepository.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException("Project not found"));

        if (!project.getOwner().getId().equals(ownerId)) {
            throw new ProjectNotFoundException("Project not found");
        }

        return ProjectConverter.modelToResponse(project);
    }

    @Transactional
    public ProjectResponse updateProject(String projectId, String ownerId, ProjectRequest projectRequest) {
        ProjectModel project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException("Project not found"));

        if (!project.getOwner().getId().equals(ownerId)) {
            throw new ProjectNotFoundException("Project not found");
        }

        if(projectRequest.name() != null){
            project.setName(projectRequest.name());
        }

        if(projectRequest.description() != null){
            project.setDescription(projectRequest.description());
        }

        ProjectModel updatedProject = projectRepository.update(project);

        return ProjectConverter.modelToResponse(updatedProject);
    }

    @Transactional
    public ProjectResponse deleteProject(String id, String ownerId) {
        ProjectModel project = projectRepository.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException("Project not found"));

        if (!project.getOwner().getId().equals(ownerId)) {
            throw new ProjectNotFoundException("Project not found");
        }

        project.setDeleted(true);
        ProjectModel updatedProject = projectRepository.update(project);

        return ProjectConverter.modelToResponse(updatedProject);
    }

    @Transactional
    public ProjectResponseWithParticipants addMember(String id, String ownerId, String memberId) {
        ProjectModel project = projectRepository.findByIdWithParticipants(id)
                .orElseThrow(() -> new ProjectNotFoundException("Project not found"));

        if (!project.getOwner().getId().equals(ownerId)) {
            throw new ProjectNotFoundException("Project not found");
        }

        if (project.getParticipants().stream().anyMatch(user -> user.getId().equals(memberId))) {
            throw new ProjectNotFoundException("Project not found");
        }

        ProjectModel updatedProject = projectRepository.addParticipant(id, memberId);

        return ProjectConverter.modelToResponseWithParticipants(updatedProject);
    }

    @Transactional
    public ProjectResponseWithParticipants removeMember(String id, String memberId, String ownerId) {
        ProjectModel project = projectRepository.findByIdWithParticipants(id)
                .orElseThrow(() -> new ProjectNotFoundException("Project not found"));

        if (!project.getOwner().getId().equals(ownerId)) {
            throw new ProjectNotFoundException("Project not found");
        }

        if (project.getParticipants().stream().noneMatch(user -> user.getId().equals(memberId))) {
            throw new ProjectNotFoundException("Project not found");
        }

        ProjectModel updatedProject = projectRepository.removeParticipant(id, memberId);

        return ProjectConverter.modelToResponseWithParticipants(updatedProject);
    }

    public Page<UserResponse> listMembers(String id, int page, int size, String sort, String direction, String ownerId) {
        Pageable pageable = PageRequest.of(page, size,
                Sort.by(direction.equalsIgnoreCase("asc") ? Sort.Direction.ASC :
                        Sort.Direction.DESC, sort));

        ProjectModel project = projectRepository.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException("Project not found"));

        if (!project.getOwner().getId().equals(ownerId)) {
            throw new ProjectNotFoundException("Project not found");
        }

        Page<UserModel> participants = projectRepository.listParticipants(id, pageable);

        return participants.map(UserConverter::modelToResponse);
    }
}
