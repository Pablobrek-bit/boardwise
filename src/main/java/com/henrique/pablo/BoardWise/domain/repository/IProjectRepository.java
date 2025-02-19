package com.henrique.pablo.BoardWise.domain.repository;

import com.henrique.pablo.BoardWise.domain.model.ProjectModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IProjectRepository {
    Page<ProjectModel> findAll(String ownerId,Pageable pageable, String search);
    Optional<ProjectModel> findById(String id);
    ProjectModel save(String ownerId, ProjectModel projectModel);
    Optional<ProjectModel> findByIdWithParticipants(String id);
    ProjectModel update(ProjectModel projectModel);
    ProjectModel addParticipant(String projectId, String participantId);
    ProjectModel removeParticipant(String projectId, String participantId);
}
