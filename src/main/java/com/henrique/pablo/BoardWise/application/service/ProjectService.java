package com.henrique.pablo.BoardWise.application.service;

import com.henrique.pablo.BoardWise.application.dto.ProjectRequest;
import com.henrique.pablo.BoardWise.application.dto.ProjectResponse;
import com.henrique.pablo.BoardWise.domain.model.ProjectModel;
import com.henrique.pablo.BoardWise.domain.repository.IProjectRepository;
import com.henrique.pablo.BoardWise.shared.exception.ProjectNotFoundException;
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

    public ProjectResponse createProject(String ownerId, @Valid ProjectRequest projectRequest) {
        ProjectModel projectModel = ProjectModel.builder()
                .name(projectRequest.name())
                .description(projectRequest.description())
                .build();

        ProjectModel savedProject = projectRepository.save(ownerId, projectModel);

        return new ProjectResponse(
                savedProject.getId(),
                savedProject.getName(),
                savedProject.getDescription(),
                ownerId
        );
    }

    public Page<ProjectResponse> listProjects(int page, int size, String sort, String direction, String ownerId, String search){
        Pageable pageable = PageRequest.of(page, size,
                Sort.by(direction.equalsIgnoreCase("asc") ? Sort.Direction.ASC :
                        Sort.Direction.DESC, sort));

        Page<ProjectModel> projects = projectRepository.findAll(ownerId,pageable, search);

        return projects.map(project -> new ProjectResponse(
                project.getId(),
                project.getName(),
                project.getDescription(),
                project.getOwner().getId()
        ));
    }

    public ProjectResponse findProjectById(String id, String ownerId) {
        ProjectModel project = projectRepository.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException("Project not found"));

        if (!project.getOwner().getId().equals(ownerId)) {
            throw new ProjectNotFoundException("Project not found");
        }

        return new ProjectResponse(
                project.getId(),
                project.getName(),
                project.getDescription(),
                project.getOwner().getId()
        );
    }

    public ProjectResponse updateProject(String id, String ownerId, ProjectRequest projectRequest) {
        ProjectModel project = projectRepository.findById(id)
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

        return new ProjectResponse(
                updatedProject.getId(),
                updatedProject.getName(),
                updatedProject.getDescription(),
                updatedProject.getOwner().getId()
        );
    }

    public ProjectResponse deleteProject(String id, String ownerId) {
        ProjectModel project = projectRepository.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException("Project not found"));

        if (!project.getOwner().getId().equals(ownerId)) {
            throw new ProjectNotFoundException("Project not found");
        }

        project.setDeleted(true);
        ProjectModel updatedProject = projectRepository.update(project);

        return new ProjectResponse(
                updatedProject.getId(),
                updatedProject.getName(),
                updatedProject.getDescription(),
                updatedProject.getOwner().getId()
        );
    }

    public ProjectResponse addMember(String id, String ownerId, String memberId) {
        ProjectModel project = projectRepository.findByIdWithParticipants(id)
                .orElseThrow(() -> new ProjectNotFoundException("Project not found"));

        if (!project.getOwner().getId().equals(ownerId)) {
            throw new ProjectNotFoundException("Project not found");
        }

        if (project.getParticipants().stream().anyMatch(user -> user.getId().equals(memberId))) {
            throw new ProjectNotFoundException("Project not found");
        }


        ProjectModel updatedProject = projectRepository.addParticipant(id, memberId);


        return new ProjectResponse(
                updatedProject.getId(),
                updatedProject.getName(),
                updatedProject.getDescription(),
                updatedProject.getOwner().getId()
        );
    }

}
