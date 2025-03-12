package com.henrique.pablo.BoardWise.infrastructure.persistence.converter;

import com.henrique.pablo.BoardWise.application.dto.project.ProjectRequest;
import com.henrique.pablo.BoardWise.application.dto.project.ProjectResponse;
import com.henrique.pablo.BoardWise.application.dto.project.ProjectResponseWithParticipants;
import com.henrique.pablo.BoardWise.domain.model.ProjectModel;
import com.henrique.pablo.BoardWise.infrastructure.persistence.entity.Project;

import java.util.stream.Collectors;

public class ProjectConverter {

    public static ProjectModel toDomain(Project entity){
        return ProjectModel.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .deleted(entity.isDeleted())
                .owner(UserConverter.toDomain(entity.getOwner()))
                .participants(entity.getParticipants().stream().map(UserConverter::toDomain).collect(Collectors.toSet()))
                .build();
    }

    public static ProjectModel toDomainWithoutParticipants(Project entity){
        return ProjectModel.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .deleted(entity.isDeleted())
                .owner(UserConverter.toDomain(entity.getOwner()))
                .build();
    }

    public static Project toEntity(ProjectModel model){
        return Project.builder()
                .id(model.getId())
                .name(model.getName())
                .description(model.getDescription())
                .deleted(model.getDeleted())
                .owner(UserConverter.toEntity(model.getOwner()))
                .build();
    }

    public static Project toEntityWithoutParticipants(ProjectModel model){
        return Project.builder()
                .id(model.getId())
                .name(model.getName())
                .description(model.getDescription())
                .owner(model.getOwner() != null ? UserConverter.toEntity(model.getOwner()) : null)
                .build();
    }

    public static ProjectResponse modelToResponse(ProjectModel model){
        return new ProjectResponse(
                model.getId(),
                model.getName(),
                model.getDescription(),
                model.getOwner().getId()
        );
    }

    public static ProjectResponseWithParticipants modelToResponseWithParticipants(ProjectModel model){
        return new ProjectResponseWithParticipants(
                model.getId(),
                model.getName(),
                model.getDescription(),
                model.getOwner().getId(),
                model.getParticipants().stream().map(UserConverter::modelToSimpleReturn).collect(Collectors.toSet())
        );
    }

    public static ProjectModel requestToDomain(ProjectRequest projectRequest){
        return ProjectModel.builder()
                .name(projectRequest.name())
                .description(projectRequest.description())
                .build();
    }
}
